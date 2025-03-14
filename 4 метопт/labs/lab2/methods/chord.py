"""
Метод хорд
"""

from typing import Callable


def solve(
        f_derivatives: list[Callable[[float], float]],
        a: float,
        b: float,
        e: float,
        e2: float
) -> tuple[float, float]:
    f = f_derivatives[0]
    f_d1 = f_derivatives[1]

    # Проверка условий применимости метода
    fa = f_d1(a)
    fb = f_d1(b)
    if fa * fb >= 0:
        raise ValueError("Производные на концах отрезка должны иметь разные знаки")

    iteration = 1
    x_hat = a  # Инициализация для первого шага

    while True:
        print(f"Шаг {iteration}:")
        print(f"Текущий интервал: [a = {a:.5f}; b = {b:.5f}]")

        # Вычисление новой точки по формуле хорд
        fa = f_d1(a)
        fb = f_d1(b)
        x_hat = a - fa * (a - b) / (fa - fb)
        f_x = f_d1(x_hat)

        print(f"x_hat = {x_hat:.5f}, f'(x_hat) = {f_x:.5f}")

        # Проверка условия останова
        if abs(f_x) <= e:
            print(f"\n|f'(x)| <= ε. Минимум найден с точностью ε = {e}")
            break

        # Обновление интервала
        if f_x > 0:
            b = x_hat
        else:
            a = x_hat

        print(f"Новый интервал: [a = {a:.5f}; b = {b:.5f}]\n")
        iteration += 1

    # Вычисление значения функции в найденной точке
    x_m = x_hat
    y_m = f(x_m)

    print(f"Минимум в точке xm = {x_m:.5f}")
    print(f"Значение в минимуме ym = {y_m:.5f}\n")

    return x_m, y_m