from decimal import Decimal, getcontext
from typing import Callable, Dict
from mpmath import mp, mpf

getcontext().prec = 100

mp.dps = 50
def chord_method(
    f: Callable[[mpf], mpf],
    a,
    b,
    epsilon,
    max_iter: int = 1000
) -> Dict:
    a = mpf(str(a))
    b = mpf(str(b))
    epsilon = mpf(str(epsilon))

    if f(a) * f(b) >= 0:
        return {"error": "Нет корней на интервале [a, b] (f(a) и f(b) одного знака)"}

    iterations = 0
    while iterations < max_iter:
        c = a - (f(a) * (b - a)) / (f(b) - f(a))
        if abs(f(c)) < epsilon:
            return {
                "root": c,
                "f_root": f(c),
                "iterations": iterations + 1,
                "status": "success"
            }
        if f(a) * f(c) < 0:
            b = c
        else:
            a = c
        iterations += 1

    return {"error": f"Метод не сошелся за {max_iter} итераций"}

def secant_method(
    f: Callable[[mpf], mpf],
    x0,
    x1,
    epsilon,
    max_iter: int = 1000
) -> Dict:
    x0 = mpf(str(x0))
    x1 = mpf(str(x1))
    epsilon = mpf(str(epsilon))

    iterations = 0
    while iterations < max_iter:
        f_x0 = f(x0)
        f_x1 = f(x1)
        if abs(f_x1) < epsilon:
            return {
                "root": x1,
                "f_root": f_x1,
                "iterations": iterations + 1,
                "status": "success"
            }
        denom = f_x1 - f_x0
        if abs(denom) < 1e-20:
            return {"error": "Малый знаменатель в методе секущих"}
        x_next = x1 - f_x1 * (x1 - x0) / denom
        x0, x1 = x1, x_next
        iterations += 1

    return {"error": f"Метод не сошелся за {max_iter} итераций"}

def simple_iteration(
    f: Callable[[mpf], mpf],
    phi: Callable[[mpf], mpf],
    x0,
    epsilon,
    max_iter: int = 1000
) -> Dict:
    x_prev = mpf(str(x0))
    epsilon = mpf(str(epsilon))

    iterations = 0
    while iterations < max_iter:
        x_next = phi(x_prev)
        if abs(x_next - x_prev) < epsilon and abs(f(x_next)) < epsilon:
            return {
                "root": x_next,
                "f_root": f(x_next),
                "iterations": iterations + 1,
                "status": "success"
            }
        x_prev = x_next
        iterations += 1

    return {"error": f"Метод не сошелся за {max_iter} итераций"}