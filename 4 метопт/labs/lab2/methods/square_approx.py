"""
Метод квадратичной аппроксимации (метод Пауэлла)
"""

from typing import Callable

def solve(
    f_derivatives: list[Callable[[float], float]],
    x0: float,
    delta: float,
    e1: float,
    e2: float,
) -> tuple[float, float]:
    f = f_derivatives[0]

    # Начальные точки
    x1 = x0
    x2 = x1 + delta
    f1, f2 = f(x1), f(x2)

    # Определение третьей точки
    if f1 < f2:
        x3 = x1 - delta
    else:
        x3 = x2 + delta
    f3 = f(x3)

    iteration = 1
    while True:
        print(f"Шаг {iteration}:")
        print(f"Текущие точки: x1={x1:.5f}, x2={x2:.5f}, x3={x3:.5f}")
        print(f"Значения функции: f(x1)={f1:.5f}, f(x2)={f2:.5f}, f(x3)={f3:.5f}")

        # Вычисление точки минимума квадратичного полинома
        numerator = (x2**2 - x3**2)*f1 + (x3**2 - x1**2)*f2 + (x1**2 - x2**2)*f3
        denominator = (x2 - x3)*f1 + (x3 - x1)*f2 + (x1 - x2)*f3

        if abs(denominator) < 1e-12:
            print("Ошибка: знаменатель равен нулю. Используется x_min.")
            x_bar = x1 if f1 < f3 else x3
        else:
            x_bar = 0.5 * numerator / denominator

        f_bar = f(x_bar)
        print(f"Аппроксимированная точка: x_bar={x_bar:.5f}, f(x_bar)={f_bar:.5f}")

        # Определение наилучшей точки
        points = [(x1, f1), (x2, f2), (x3, f3), (x_bar, f_bar)]
        x_min, f_min = min(points, key=lambda x: x[1])

        # Проверка условий останова
        rel_f = abs((f_min - f_bar) / f_bar) if f_bar != 0 else 0
        rel_x = abs((x_min - x_bar) / x_bar) if x_bar != 0 else 0

        print(f"Относительная погрешность f: {rel_f:.5f}, x: {rel_x:.5f}\n")

        if rel_f < e1 and rel_x < e2:
            print(f"Условия точности выполнены!")
            break

        if x_bar < x1 or x_bar > x3:
            print("Условие пункта в) выполнено: x_bar не принадлежит [x1; x3]")
            x1 = x_bar
            x2 = x1 + delta
            x3 = x1 - delta if f(x1 - delta) < f(x1 + delta) else x2 + delta
            f1, f2, f3 = f(x1), f(x2), f(x3)
            continue

        # Обновление точек
        sorted_points = sorted([x1, x2, x3, x_bar], key=lambda x: f(x))
        x1, x2, x3 = sorted_points[:3]
        f1, f2, f3 = f(x1), f(x2), f(x3)

        iteration += 1

    print(f"\nМинимум в точке xm = {x_bar:.5f}")
    print(f"Значение в минимуме ym = {f_bar:.5f}\n")
    return x_bar, f_bar