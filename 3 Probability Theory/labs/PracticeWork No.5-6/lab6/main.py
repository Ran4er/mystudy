import math
import matplotlib.pyplot as plt

# Исходные данные (14 варианта)
data = [-0.53, -0.93, 0.48, -1.55, -1.34, -0.04, -0.84, 0.57, 0.76, 0.30, -0.87, -0.41, 0.81, -1.42, -0.61, -0.33, -1.33, 0.62, -0.48, -0.35]

# Сортируем данные
sorted_data = sorted(data)

# 1. Вариационный ряд
print("Вариационный ряд:", sorted_data)

# 2. Экстремальные значения и размах
min_val = min(sorted_data)
max_val = max(sorted_data)
range_val = max_val - min_val
print("Минимум:", min_val, "Максимум:", max_val, "Размах:", range_val)

# 3. Оценки математического ожидания и среднеквадратического отклонения
mean = sum(sorted_data) / len(sorted_data)
variance = sum((x - mean) ** 2 for x in sorted_data) / len(sorted_data)
std_dev = math.sqrt(variance)
print("Математическое ожидание:", mean)
print("Среднеквадратическое отклонение:", std_dev)

# 4. Эмпирическая функция распределения
ecdf = [(x, (sorted_data.index(x) + 1) / len(sorted_data)) for x in sorted_data]
print("Эмпирическая функция распределения (x, F(x)):", ecdf)

# Построение графика эмпирической функции распределения
plt.step([x[0] for x in ecdf], [x[1] for x in ecdf], where="post")
plt.title("Эмпирическая функция распределения")
plt.xlabel("Значение")
plt.ylabel("Частота")
plt.grid()
plt.show()

# 5. Гистограмма и полигон частот
# Группируем данные
intervals = [(min_val + i * range_val / 5, min_val + (i + 1) * range_val / 5) for i in range(5)]
grouped = [sum(1 for x in sorted_data if low <= x < high) for low, high in intervals]

# Гистограмма
plt.bar(range(len(grouped)), grouped, width=0.8, tick_label=[f"{low:.1f}-{high:.1f}" for low, high in intervals])
plt.title("Гистограмма частот")
plt.xlabel("Интервалы")
plt.ylabel("Частота")
plt.grid()
plt.show()

# Полигон частот
plt.plot(range(len(grouped)), grouped, marker='o')
plt.title("Полигон частот")
plt.xlabel("Интервалы")
plt.ylabel("Частота")
plt.grid()
plt.show()