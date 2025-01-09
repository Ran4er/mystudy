import numpy as np
import matplotlib.pyplot as plt

def generate_initial_points():
    theta = np.linspace(0.0001, np.pi - 0.0001, 100)  # Угол от 0 до pi
    r = np.linspace(0.0001, 0.9999, 200)  # Радиусы
    R, T = np.meshgrid(r, theta)
    X = R * np.cos(T)
    Y = R * np.sin(T)
    return X + 1j * Y

def f(z):
    return np.acosh(-0.5 * (z + 1 / z))

def plot_complex_points(Z, title, filename, color='blue', size=0.5):
    plt.figure(figsize=(8, 6))
    plt.scatter(Z.real, Z.imag, color=color, s=size)
    plt.title(title)
    plt.xlabel('Re(z)')
    plt.ylabel('Im(z)')
    plt.axis('equal')
    plt.grid(True)
    plt.savefig(filename)  # Сохраняем изображение в файл
    plt.close()  # Закрываем изображение, чтобы не мешало при создании следующего

# Генерируем начальное множество
Z_initial = generate_initial_points()
# Визуализация начального множества
plot_complex_points(Z_initial, 'Начальное множество: Полукруг |z| < 1, Im(z) > 0', 'task1.1.png')

# Применяем первое преобразование: w = -0.5 * (z + 1/z)
W1 = np.exp(1j * (3*np.pi/4)) * Z_initial
plot_complex_points(W1, 'После первого преобразования: w = -0.5 * (z + 1/z)', 'task1.2.png', color='green')

plot_complex_points(f(W1), 'После первого преобразования: w = -0.5 * (z + 1/z)', 'task1.png', color='green')

# Применяем второе преобразование: w = exp(i * phi) * z) -1
W2 = np.exp(1j*(3*np.pi)/4) * f(W1)
mean_W3 = np.mean(W2)

# Задаем точку на Re(z) = -1 и Im(z) = -i
target_point = -1.2 - 2j  # Точка, с которой должно пересекаться множество

# Считаем смещение
shift = target_point - mean_W3

# Применяем сдвиг
W3_shifted = W2 + shift
plot_complex_points(W3_shifted, 'После второго преобразования', 'task1.3.png', color='red')

W1_apa = -0.5 * (Z_initial + (1/Z_initial))
plot_complex_points(W1_apa, 'После первого преобразования: w = -0.5 * (z + 1/z)', 'task1.4.png', color='green')


W2 = f(W1)
plot_complex_points(W2, 'После первого преобразования: w = -0.5 * (z + 1/z)', 'task5.png', color='green')