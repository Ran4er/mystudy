import mpmath
from collections import defaultdict

def compute_integral(method_func, f, a, b, epsilon, initial_n, order):
    history = defaultdict(dict)
    n = initial_n
    max_iterations = 40
    prev_error = mpmath.inf
    
    for iteration in range(max_iterations):
        integral_n = method_func(f, a, b, n)
        integral_2n = method_func(f, a, b, 2*n)
        
        error = mpmath.fabs((integral_n - integral_2n)/integral_2n) if integral_2n !=0 else mpmath.fabs(integral_n - integral_2n)
        
        history[iteration] = {
            'n': n,
            'integral': integral_2n,
            'error': error,
            'status': 'running'
        }
        
        if error < epsilon or (prev_error - error) < 1e-18:
            history[iteration]['status'] = 'converged'
            return integral_2n, 2*n, history
        
        prev_error = error
        n *= 2
    
    history[iteration]['status'] = 'diverged'
    raise ValueError(f"Точность не достигнута. Лучшая погрешность: {mpmath.nstr(error, 4)}", history)
