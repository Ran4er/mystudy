"""
Метод покоординатного спуска
"""

from methods.golden_section import solve as golden_section_solve

def solve(f_derivatives, initial_point, epsilon, max_iter=1000):
    x = list(initial_point)  # Начальная точка
    n = len(x)               # Количество координат
    iteration = 0

    while iteration < max_iter:
        prev_x = x.copy()  # Сохраняем предыдущее состояние

        # Проход по всем координатам
        for i in range(n):
            # Функция для минимизации по i-ой координате
            def func(alpha):
                new_x = x.copy()
                new_x[i] += alpha  # Изменяем только i-ую координату
                return f_derivatives[0](new_x)

            # Поиск оптимального шага alpha методом золотого сечения
            alpha_opt, _ = golden_section_solve([func], -1, 1, epsilon)
            x[i] += alpha_opt  # Обновляем i-ую координату

        # Проверка условия остановки (изменение всех координат < epsilon)
        if all(abs(x[i] - prev_x[i]) < epsilon for i in range(n)):
            break

        iteration += 1

    return x, f_derivatives[0](x)  # Возврат результата