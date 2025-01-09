import math
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict
import os

class Chart:
    def __init__(self, x_label: str, y_label: str, title: str):
        self.x_label = x_label
        self.y_label = y_label
        self.title = title
        self.fig, self.ax = plt.subplots()
        self.ax.set_title(title)
        self.ax.set_xlabel(x_label)
        self.ax.set_ylabel(y_label)
        self.ax.grid(True)

    def add_line(self, x: list, y: list, label: str = None, linewidth: float = 2, marker: str = None):
        self.ax.plot(x, y, label=label, linewidth=linewidth, marker=marker)

    def add_polygonal_line(self, x: list, y: list, label: str = None, linewidth: float = 2):
        self.ax.plot(x, y, label=label, linewidth=linewidth)

    def add_histogram(self, x: list, heights: list, width: float, label: str = None):
        self.ax.bar(x, heights, width=width, align='edge', alpha=0.5, label=label, edgecolor='black')

    def save_png(self, name: str):
        self.ax.legend(loc='upper left', bbox_to_anchor=(1.05, 1), fontsize='small', title="History")
        plt.tight_layout(rect=(0.0, 0.0, 0.85, 1.0))
        plt.savefig(f"{name}.png")
        plt.show()
        plt.close(self.fig)

class ProbabilityTheory:
    def __init__(self, values: list):
        self.values = sorted(values)
        self.n = len(values)
        self.xi = []
        self.ni = []
        self.pi = []

    def get_var_values(self):
        print("! Вариационный ряд:")
        print(self.values)
        print("\n")

    def calculate_statistical_series(self):
        print("! Статистический ряд:")
        frequency = defaultdict(int)
        for x in self.values:
            frequency[x] += 1
        for value in sorted(frequency.keys()):
            print(f"x = {value:.2f}, n = {frequency[value]}")
        print("\n")


    def get_extreme_values(self):
        print("! Экстремальные значения:")
        print(f"MIN = {self.values[0]}")
        print(f"MAX = {self.values[-1]}\n")

    def get_selection_size(self):
        range_val = self.values[-1] - self.values[0]
        print("! Размах выборки:")
        print(f"{range_val}\n")

    def calculate_numeric_characteristics(self):
        print("! Числовые характеристики:")

        # Медиана
        if self.n % 2 == 1:
            median = self.values[self.n // 2]
        else:
            median = (self.values[self.n // 2 - 1] + self.values[self.n // 2]) / 2
        print(f"Медиана: {median:.2f}")

        # Мода
        frequency = defaultdict(int)
        for x in self.values:
            frequency[x] += 1
        mode = [k for k, v in frequency.items() if v == max(frequency.values())]
        if len(mode) == len(frequency):
            mode_value = "Нет явной моды"
        else:
            mode_value = ", ".join(f"{m:.2f}" for m in mode)
        print(f"Мода: {mode_value}\n")

    def discrepancy_calculation(self):
        frequency = defaultdict(int)
        for x in self.values:
            frequency[x] += 1

        for x in sorted(frequency.keys()):
            self.xi.append(x)
            self.ni.append(frequency[x])
            self.pi.append(frequency[x] / self.n)

        expected_value = sum(x * p for x, p in zip(self.xi, self.pi))
        print("! Оценка математического ожидания")
        print(f"{expected_value:.2f}\n")

        variance = sum(((x - expected_value) ** 2) * n for x, n in zip(self.xi, self.ni)) / self.n
        corrected_variance = (self.n / (self.n - 1)) * variance if self.n > 1 else 0
        print("! Дисперсия")
        print(f"{variance:.2f}\n")
        print("! Исправленная дисперсия")
        print(f"{corrected_variance:.2f}\n")
        print("! Cреднеквадратическоe отклонение")
        print(f"{math.sqrt(variance):.2f}\n")
        print("! Исправленное СКО")
        print(f"{math.sqrt(corrected_variance):.2f}\n")

    def get_h(self):
        return (self.values[-1] - self.values[0]) / (1 + math.log2(self.n)) # Формула Стерджеса

    def get_m(self):
        return math.ceil(1 + math.log2(self.n))
    
    def calculate_empiric_function(self):
        print("\t\t\t! Функция")
        print(f"\t\t\tx\t<=\t{self.xi[0]:.2f}\t->\t0.00")

        h = 0
        chart = Chart("x", "F(X)", "Эмпирическая функция")


        x_start = self.values[0] - 1
        chart.add_line([x_start, self.xi[0]], [0, 0], label=f"x <= {self.xi[0]:.2f}")


        for i in range(len(self.xi)):
            h += self.pi[i]
            if i < len(self.xi) - 1:
                print(f"{self.xi[i]:.2f}\t<\tx\t<=\t{self.xi[i + 1]:.2f}\t->\t{h:.2f}")
                chart.add_line([self.xi[i], self.xi[i + 1]], [h, h],
                               label=f"{self.xi[i]:.2f} < x <= {self.xi[i + 1]:.2f}")


        print(f"{self.xi[-1]:.2f}\t<\tx\t\t\t\t->\t{h:.2f}")
        chart.add_line([self.xi[-1], self.xi[-1] + 1], [h, h], label=f"{self.xi[-1]:.2f} < x")


        chart.save_png("EmpiricFunction")

    def draw_frequency_polygon(self):
        h = self.get_h()
        m = self.get_m()
        frequency = [0] * m
        x_start = self.values[0] - h / 2

        for value in self.values:
            index = int((value - x_start) // h)
            if index >= m:
                index = m - 1
            frequency[index] += 1

        print("! Полигон частот:")
        xs = []
        ys = []
        chart = Chart("x", "p_i", "Полигон частот")
        for i in range(m):
            freq = frequency[i] / self.n 
            bin_center = x_start + h / 2 + i * h
            xs.append(bin_center)
            ys.append(freq)
            print(f"[ {x_start + i * h:.2f} : {x_start + (i + 1) * h:.2f} ) -> {freq:.2f}")

        chart.add_polygonal_line(xs, ys, label="Frequency Polygon")
        chart.save_png("FrequencyPolygon")

    def draw_histogram(self):
        h = self.get_h()
        m = self.get_m()
        frequency = [0] * m
        x_start = self.values[0] - h / 2

        for value in self.values:
            index = int((value - x_start) // h)
            if index >= m:
                index = m - 1
            frequency[index] += 1

        bins = [x_start + i * h for i in range(m + 1)]
        heights = [(freq / self.n) / h for freq in frequency]

        chart = Chart("x", "p_i / h", "Гистограмма частот")

        chart.add_histogram(bins[:-1], heights, width=h, label="Histogram")
        chart.save_png("Histogram")

    def print_data(self):
        print("\n\n! DEBUG:\n")
        for x, n, p in zip(self.xi, self.ni, self.pi):
            print(f"{x} {n} {p}")

def read_input(file_path: str, n: int = 20) -> list:
    if not os.path.exists(file_path):
        raise FileNotFoundError(f"File {file_path} not found.")

    with open(file_path, 'r') as file:
        data = []
        for line in file:
            parts = line.strip().split()
            for part in parts:
                if part:
                    data.append(float(part.replace(',', '.')))
                    if len(data) == n:
                        break
            if len(data) == n:
                break

    if len(data) < n:
        raise ValueError(f"Expected {n} numbers, but got {len(data)}.")

    return data

def main():
    input_file = "input.txt"
    try:
        elements = read_input(input_file)
    except Exception as e:
        print(f"Error reading input: {e}")
        return

    runner = ProbabilityTheory(elements)

    # Вариационный ряд
    runner.get_var_values()

    # Статистический ряд
    runner.calculate_statistical_series()

    # Экстремальные значения
    runner.get_extreme_values()

    # Размах
    runner.get_selection_size()

    # Числовые характеристики
    runner.calculate_numeric_characteristics()

    # Оценки математического ожидания и среднеквадратического отклонения
    runner.discrepancy_calculation()

    # Эмпирическая функция распределения и её график
    runner.calculate_empiric_function()

    # Полигон частот
    runner.draw_frequency_polygon()

    # Гистограмма
    runner.draw_histogram()

    # Отладочные данные
    # runner.print_data()

if __name__ == "__main__":
    main()