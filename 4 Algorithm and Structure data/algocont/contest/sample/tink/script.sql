/* Проект «Секреты Тёмнолесья»
 * Цель проекта: изучить влияние характеристик игроков и их игровых персонажей 
 * на покупку внутриигровой валюты «райские лепестки», а также оценить 
 * активность игроков при совершении внутриигровых покупок
 * 
 * Автор: Савинова Алина Константиновна
 * Дата: 11.05.2025
*/

-- Часть 1. Исследовательский анализ данных
-- Задача 1. Исследование доли платящих игроков

-- 1.1. Доля платящих пользователей по всем данным:
-- Напишите ваш запрос здесь
SELECT
    COUNT(DISTINCT id) AS total_players,
    COUNT(DISTINCT CASE WHEN payer = 1 THEN id END) AS paying_players,
    ROUND(
        COUNT(DISTINCT CASE WHEN payer = 1 THEN id END) * 100.0 / 
        COUNT(DISTINCT id),
        2
    ) AS paying_players_percentage
FROM fantasy.users;

-- 1.2. Доля платящих пользователей в разрезе расы персонажа:
-- Напишите ваш запрос здесь
SELECT
    r.race AS character_race,
    COUNT(DISTINCT CASE WHEN u.payer = 1 THEN u.id END) AS paying_players_count,
    COUNT(DISTINCT u.id) AS total_players_count,
    ROUND(
        COUNT(DISTINCT CASE WHEN u.payer = 1 THEN u.id END) * 100.0 / 
        NULLIF(COUNT(DISTINCT u.id), 0),
        2
    ) AS paying_players_percentage
FROM 
    fantasy.users u
LEFT JOIN 
    fantasy.race r ON u.race_id = r.race_id
GROUP BY 
    r.race
ORDER BY 
    paying_players_percentage DESC;

-- Задача 2. Исследование внутриигровых покупок
-- 2.1. Статистические показатели по полю amount:
-- Напишите ваш запрос здесь
SELECT 
    COUNT(*) AS total_transactions,
    SUM(amount) AS total_amount,
    MIN(amount) AS min_amount,
    MAX(amount) AS max_amount,
    ROUND(AVG(amount)::numeric, 2) AS avg_amount,
    ROUND(PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY amount)::numeric, 2) AS median_amount,
    ROUND(STDDEV(amount)::numeric, 2) AS stddev_amount
FROM fantasy.events
WHERE amount > 0;

-- 2.2: Аномальные нулевые покупки:
-- Напишите ваш запрос здесь
WITH total AS (
    SELECT COUNT(*) AS total_count FROM fantasy.events
),
zero AS (
    SELECT COUNT(*) AS zero_count FROM fantasy.events WHERE amount = 0
)
SELECT 
    zero_count AS absolute_zero_purchases,
    ROUND(zero_count * 100.0 / NULLIF(total_count, 0), 2) AS zero_percentage
FROM total, zero;

-- 2.3: Сравнительный анализ активности платящих и неплатящих игроков:
-- Напишите ваш запрос здесь
WITH player_stats AS (
    SELECT 
        u.payer,
        u.id AS player_id,
        COUNT(e.transaction_id) AS total_purchases,
        SUM(e.amount) AS total_spent
    FROM fantasy.users u
    LEFT JOIN fantasy.events e ON u.id = e.id
    WHERE e.amount > 0
    GROUP BY u.payer, u.id
)
SELECT 
    CASE WHEN payer = 1 THEN 'Платящие' ELSE 'Неплатящие' END AS player_group,
    COUNT(player_id) AS total_players,
    ROUND(AVG(total_purchases), 2) AS avg_purchases_per_player,
    ROUND(AVG(total_spent)::numeric, 2) AS avg_spent_per_player
FROM player_stats
GROUP BY payer;

-- 2.4: Популярные эпические предметы:
-- Напишите ваш запрос здесь
WITH total_sales AS (
    SELECT COUNT(*) AS total FROM fantasy.events WHERE amount > 0
),
item_stats AS (
    SELECT 
        i.game_items AS item_name,
        COUNT(e.transaction_id) AS sales_count,
        COUNT(DISTINCT e.id) AS unique_buyers
    FROM fantasy.items i
    JOIN fantasy.events e ON i.item_code = e.item_code
    WHERE e.amount > 0
    GROUP BY i.game_items
)
SELECT 
    item_name,
    sales_count,
    ROUND(sales_count * 100.0 / NULLIF((SELECT total FROM total_sales), 0), 2) AS sales_percentage,
    ROUND(unique_buyers * 100.0 / NULLIF((SELECT COUNT(DISTINCT id) FROM fantasy.users), 0), 2) AS buyers_percentage
FROM item_stats
ORDER BY sales_count DESC;

-- Часть 2. Решение ad hoc-задач
-- Задача 1. Зависимость активности игроков от расы персонажа:
WITH race_players AS (
    SELECT 
        r.race,
        COUNT(DISTINCT u.id) AS total_players
    FROM fantasy.users u
    LEFT JOIN fantasy.race r ON u.race_id = r.race_id
    GROUP BY r.race
), purchasing_players AS (
    SELECT 
        r.race,
        u.id AS player_id,
        u.payer,
        COUNT(e.transaction_id) AS total_purchases,
        SUM(e.amount) AS total_spent
    FROM fantasy.users u
    LEFT JOIN fantasy.race r ON u.race_id = r.race_id
    LEFT JOIN fantasy.events e ON u.id = e.id AND e.amount > 0
    GROUP BY r.race, u.id, u.payer
), race_purchase_stats AS (
    SELECT 
        race,
        COUNT(DISTINCT player_id) AS players_with_purchases,
        COUNT(CASE WHEN payer = 1 THEN player_id END) AS paying_players,
        SUM(total_purchases) AS total_purchases,
        SUM(total_spent) AS total_spent
    FROM purchasing_players
    GROUP BY race
) SELECT 
    rp.race,
    rp.total_players,
    COALESCE(rps.players_with_purchases, 0) AS players_with_purchases,
    ROUND(
        (COALESCE(rps.players_with_purchases * 100.0 / NULLIF(rp.total_players, 0), 0))::numeric, 
        2
    ) AS purchase_activity_percentage,
    ROUND(
        (COALESCE(rps.paying_players * 100.0 / NULLIF(rps.players_with_purchases, 0), 0))::numeric, 
        2
    ) AS paying_among_active_percentage,
    ROUND(
        (COALESCE(rps.total_purchases * 1.0 / NULLIF(rps.players_with_purchases, 0), 0))::numeric, 
        2
    ) AS avg_purchases_per_player,
    ROUND(
        (COALESCE(rps.total_spent * 1.0 / NULLIF(rps.total_purchases, 0), 0))::numeric, 
        2
    ) AS avg_purchase_cost,
    ROUND(
        (COALESCE(rps.total_spent * 1.0 / NULLIF(rps.players_with_purchases, 0), 0))::numeric, 
        2
    ) AS avg_total_spent_per_player FROM race_players rp
LEFT JOIN race_purchase_stats rps ON rp.race = rps.race
ORDER BY rp.race;

-- Задача 2: Частота покупок
WITH purchase_intervals AS (
    SELECT 
        id AS player_id,
        TO_DATE(date, 'YYYY-MM-DD') AS purchase_date,
        LAG(TO_DATE(date, 'YYYY-MM-DD')) OVER (PARTITION BY id ORDER BY TO_DATE(date, 'YYYY-MM-DD')) AS prev_purchase_date,
        amount
    FROM fantasy.events
    WHERE amount > 0
), days_between AS (
    SELECT 
        player_id,
        purchase_date,
        (purchase_date - prev_purchase_date) AS days_diff
    FROM purchase_intervals
    WHERE prev_purchase_date IS NOT NULL
), player_stats AS (
    SELECT 
        u.id AS player_id,
        u.payer,
        COUNT(d.player_id) + 1 AS total_purchases,
        AVG(d.days_diff) AS avg_days_between
    FROM fantasy.users u
    JOIN days_between d ON u.id = d.player_id
    GROUP BY u.id, u.payer
    HAVING COUNT(d.player_id) + 1 >= 25
), ranked_players AS (
    SELECT 
        player_id,
        payer,
        total_purchases,
        avg_days_between,
        NTILE(3) OVER (ORDER BY avg_days_between ASC) AS frequency_group
    FROM player_stats
)
SELECT 
    CASE frequency_group
        WHEN 1 THEN 'высокая частота'
        WHEN 2 THEN 'умеренная частота'
        WHEN 3 THEN 'низкая частота'
    END AS frequency_category,
    COUNT(player_id) AS total_players,
    COUNT(CASE WHEN payer = 1 THEN player_id END) AS paying_players_count,
    ROUND(COUNT(CASE WHEN payer = 1 THEN player_id END) * 100.0 / COUNT(player_id), 2) AS paying_players_percentage,
    ROUND(AVG(total_purchases), 2) AS avg_purchases_per_player,
    ROUND(AVG(avg_days_between), 2) AS avg_days_between
FROM ranked_players
GROUP BY frequency_group
ORDER BY frequency_group;
