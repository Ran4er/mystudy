import mpmath
from concurrent.futures import ThreadPoolExecutor

def parallel_integrate(f, a, b, n, worker):
    h = (b - a) / n
    with ThreadPoolExecutor() as executor:
        try:
            futures = [executor.submit(worker, f, a + i*h, h) for i in range(n)]
            return sum(f.result() for f in futures)
        except Exception as e:
            raise ValueError(f"Ошибка в параллельных вычислениях: {str(e)}")

def rectangle_left(f, a, b, n):
    def worker(f, x, h):
        return f(x) * h
    return parallel_integrate(f, a, b, n, worker)

def rectangle_right(f, a, b, n):
    def worker(f, x, h):
        return f(x + h) * h 
    return parallel_integrate(f, a, b, n, worker)

def rectangle_mid(f, a, b, n):
    def worker(f, x, h):
        return f(x + h/2) * h
    return parallel_integrate(f, a, b, n, worker)

def trapezoid(f, a, b, n):
    def worker(f, x, h):
        return (f(x) + f(x + h)) * h / 2
    return parallel_integrate(f, a, b, n, worker)

def simpson(f, a, b, n):
    try:
        if n % 2 != 0:
            n += 1
        h = (b - a) / n
        if h == 0:
            return mpmath.mpf(0)
            
        odd = even = mpmath.mpf(0)
        for i in range(1, n, 2):
            odd += f(a + i*h)
        for i in range(2, n-1, 2):
            even += f(a + i*h)
            
        return (f(a) + f(b) + 4*odd + 2*even) * h / 3
    except Exception as e:
        raise ValueError(f"Ошибка метода Симпсона: {str(e)}")

METHODS = {
    1: {'name': 'Левые прямоугольники', 'func': rectangle_left, 'order': 1},
    2: {'name': 'Правые прямоугольники', 'func': rectangle_right, 'order': 1},
    3: {'name': 'Средние прямоугольники', 'func': rectangle_mid, 'order': 2},
    4: {'name': 'Метод трапеций', 'func': trapezoid, 'order': 2},
    5: {'name': 'Метод Симпсона', 'func': simpson, 'order': 4},
}
