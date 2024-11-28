import math
import matplotlib.pyplot as plt

# Исходные данные
data = [-0.53, -0.93, 0.48, -1.55, -1.34, -0.04, -0.84, 0.57, 0.76, 0.30, -0.87, -0.41, 0.81, -1.42, -0.61, -0.33, -1.33, 0.62, -0.48, -0.35]

# Сортируем данные
sorted_data = sorted(data)

# 1. Вариационный ряд
print("Вариационный ряд:", sorted_data)

# 2. Статистический ряд
freq = {x: sorted_data.count(x) for x in sorted_data}
print("Статистический ряд (значение: частота):", freq)

# 3. Числовые характеристики
n = len(sorted_data)
mean = sum(sorted_data) / n  # Выборочное среднее
variance = sum((x - mean) ** 2 for x in sorted_data) / n  # Выборочная дисперсия
corrected_variance = sum((x - mean) ** 2 for x in sorted_data) / (n - 1)  # Исправленная дисперсия
std_dev = math.sqrt(variance)  # Выборочное СКО
corrected_std_dev = math.sqrt(corrected_variance)  # Исправленное СКО

print(f"Выборочное среднее: {mean:.2f}")
print(f"Выборочная дисперсия: {variance:.2f}")
print(f"Исправленная дисперсия: {corrected_variance:.2f}")
print(f"Выборочное стандартное отклонение: {std_dev:.2f}")
print(f"Исправленное стандартное отклонение: {corrected_std_dev:.2f}")

# 4. Функция распределения (аналитический вид и график)
ecdf = [(x, sorted_data.index(x) / n) for x in sorted_data]

# Аналитический вид: F(x) = количество элементов <= x / n
def empirical_distribution_function(x):
    return sum(1 for i in sorted_data if i <= x) / n

print("Эмпирическая функция распределения:")
for x in sorted_data:
    print(f"F({x}) = {empirical_distribution_function(x):.2f}")

# График функции распределения
x_vals = sorted(set(sorted_data))
y_vals = [empirical_distribution_function(x) for x in x_vals]
plt.step(x_vals, y_vals, where='post')
plt.title("Эмпирическая функция распределения")
plt.xlabel("Значение")
plt.ylabel("F(x)")
plt.grid()
plt.show()

# 5. Группированный (интервальный) ряд
# Число интервалов по формуле Стерджесса
num_intervals = 1 + math.ceil(math.log2(n))
interval_width = math.ceil((max(sorted_data) - min(sorted_data)) / num_intervals)

intervals = [
    (min(sorted_data) + i * interval_width, min(sorted_data) + (i + 1) * interval_width)
    for i in range(num_intervals)
]

grouped_freq = [sum(1 for x in sorted_data if low <= x < high) for low, high in intervals]

# Вывод группированного ряда
print("Группированный ряд (интервалы: частота):")
for (low, high), freq in zip(intervals, grouped_freq):
    print(f"[{low}; {high}): {freq}")

# Гистограмма
plt.bar(
    range(num_intervals),
    grouped_freq,
    width=0.8,
    tick_label=[f"[{low}; {high})" for low, high in intervals],
)
plt.title("Гистограмма частот")
plt.xlabel("Интервалы")
plt.ylabel("Частота")
plt.grid()
plt.show()

# Полигон
plt.plot(range(num_intervals), grouped_freq, marker='o')
plt.title("Полигон частот")
plt.xlabel("Интервалы")
plt.ylabel("Частота")
plt.grid()
plt.show()
