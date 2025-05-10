from decimal import Decimal, getcontext
from typing import Callable, List, Dict, Tuple, Any
import random

from mpmath import mpf, mp

getcontext().prec = 30

def simple_iteration_system(
    phi_functions: List[Callable[..., Decimal]],
    initial_guess: List[Decimal],
    epsilon: Decimal,
    max_iter: int = 1000
) -> Dict[str, Any]:
    """
    Метод простой итерации для систем нелинейных уравнений
    
    Параметры:
        phi_functions: Список функций преобразования [φ1, φ2,...]
        initial_guess: Начальное приближение [x1_0, x2_0,...]
        epsilon: Требуемая точность
        max_iter: Максимальное число итераций

    Возвращает:
        Словарь с результатом или ошибкой
    """
    n = len(phi_functions)
    current = [mpf(str(x)) for x in initial_guess]
    history = [current.copy()]
    epsilon = mpf(str(epsilon))

    for iteration in range(max_iter):
        new = [phi(*current) for phi in phi_functions]
        max_error = max(abs(new[i] - current[i]) for i in range(n))

        if any(mp.isnan(val) for val in new):
            return {
                "error": "Решение содержит недопустимые значения (NaN)",
                "history": history,
                "status": "fail"
            }

        current = new
        history.append(current.copy())
        
        if max_error < epsilon:
            return {
                "solution": current,
                "errors": [max_error],
                "iterations": iteration + 1,
                "history": history,
                "status": "success"
            }
    
    return {
        "error": "Метод не сошелся за указанное число итераций",
        "history": history,
        "status": "fail"
    }

def check_convergence(
    phi_functions: List[Callable[..., Decimal]],
    partial_derivatives: List[List[Callable[..., Decimal]]],
    domain: List[Tuple[Decimal, Decimal]],
    samples: int = 100
) -> bool:
    """
    Проверка достаточного условия сходимости
    
    Параметры:
        partial_derivatives: Матрица частных производных ∂φi/∂xj
        domain: Область определения [ (a1,b1), (a2,b2),... ]
        samples: Число проверочных точек
        
    Возвращает:
        True если выполняется условие сходимости
    """
    n = len(phi_functions)
    q = Decimal(0)

    for _ in range(samples):
        point = [
            domain[i][0] + (domain[i][1]-domain[i][0])*Decimal(str(random.random()))
            for i in range(n)
        ]
        
        current_q = Decimal(0)
        for i in range(n):
            sum_deriv = sum(
                abs(partial_derivatives[i][j](*point)) 
                for j in range(n)
            )
            if sum_deriv > current_q:
                current_q = sum_deriv
        
        if current_q > q:
            q = current_q
    
    return q < Decimal('1')