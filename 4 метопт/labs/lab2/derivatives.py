from math import sin, cos


def f(x: float) -> float:
    """Функция f."""
    return x**2 + x + sin(x)


def f_derivative_1(x: float) -> float:
    """Первая производная функции f."""
    return 2*x + 1 + cos(x)


def f_derivative_2(x: float) -> float:
    """Вторая производная функции f."""
    return 2 - sin(x)


F_DERIVATIES = [
    f,
    f_derivative_1,
    f_derivative_2,
]