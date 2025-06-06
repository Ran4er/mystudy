# interpolation.py

import numpy as np
import math
from differences import finite_differences


def lagrange_interp(xs: np.ndarray, ys: np.ndarray, x: float) -> float:
    """
    Интерполяция по формуле Лагранжа.
    """
    n = len(xs)
    if n < 2:
        raise ValueError("Нужно минимум две точки для интерполяции")
    total = 0.0
    for i in range(n):
        term = ys[i]
        for j in range(n):
            if i != j:
                if xs[i] == xs[j]:
                    raise ZeroDivisionError(f"Повторяющиеся узлы xs[{i}] и xs[{j}]")
                term *= (x - xs[j]) / (xs[i] - xs[j])
        total += term
    return total


def newton_interp(xs: np.ndarray, diff_table: list, x: float) -> float:
    """
    Интерполяция по схеме Ньютона с разделёнными разностями.
    diff_table — это результат finite_differences(xs, ys).
    """
    n = len(xs)
    if n < 2:
        raise ValueError("Нужно минимум две точки для интерполяции")
    result = diff_table[0][0]
    product = 1.0
    for k in range(1, n):
        product *= (x - xs[k-1])
        result += diff_table[k][0] * product
    return result


def gauss_interp(xs: np.ndarray, ys: np.ndarray, x: float) -> float:
    """
    Центральная схема Гаусса через перестановку узлов.

    1) Проверяем, что сетка равномерная.
    2) Находим центральный индекс m = n//2.
    3) Формируем новый порядок точек: [m, m+1, m-1, m+2, m-2, ...].
    4) Строим таблицу разделённых разностей для этой упорядоченной сетки.
    5) Вызываем newton_interp на упорядоченных данных.
    """
    n = len(xs)
    if n < 2:
        raise ValueError("Нужно минимум две точки для интерполяции")

    # Проверка равномерности шага
    h = xs[1] - xs[0]
    for i in range(1, n):
        if not math.isclose(xs[i] - xs[i-1], h, rel_tol=1e-9, abs_tol=1e-9):
            raise ValueError("Сетка должна быть равномерной для схемы Гаусса")

    # Центральный индекс
    m = n // 2

    # Формируем порядок индексов: m, m+1, m-1, m+2, m-2, ...
    order = [m]
    for k in range(1, n):
        if m + k < n:
            order.append(m + k)
        if m - k >= 0:
            order.append(m - k)

    # Упорядоченные xs и ys
    xs_ord = np.array([xs[i] for i in order])
    ys_ord = np.array([ys[i] for i in order])

    # Таблица разделённых разностей на упорядоченных данных
    diff_ord = finite_differences(xs_ord, ys_ord)

    # Интерполируем в точке x с помощью Ньютона
    return newton_interp(xs_ord, diff_ord, x)
