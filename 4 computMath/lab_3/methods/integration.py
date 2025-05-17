def rectangle_left(f, a, b, n):
    h = (b - a) / n
    return h * sum(f(a + i * h) for i in range(n))

def rectangle_right(f, a, b, n):
    h = (b - a) / n
    return h * sum(f(a + (i + 1) * h) for i in range(n))

def rectangle_mid(f, a, b, n):
    h = (b - a) / n
    return h * sum(f(a + (i + 0.5) * h) for i in range(n))

def trapezoid(f, a, b, n):
    h = (b - a) / n
    return h/2 * (f(a) + f(b) + 2 * sum(f(a + i * h) for i in range(1, n)))

def simpson(f, a, b, n):
    if n % 2 != 0:
        n += 1
    h = (b - a) / n
    integral = f(a) + f(b)
    for i in range(1, n):
        x = a + i * h
        integral += 4 * f(x) if i % 2 else 2 * f(x)
    return integral * h / 3

METHODS = {
    1: {'name': 'Левые прямоугольники', 'func': rectangle_left, 'order': 1},
    2: {'name': 'Правые прямоугольники', 'func': rectangle_right, 'order': 1},
    3: {'name': 'Средние прямоугольники', 'func': rectangle_mid, 'order': 2},
    4: {'name': 'Метод трапеций', 'func': trapezoid, 'order': 2},
    5: {'name': 'Метод Симпсона', 'func': simpson, 'order': 4},
}
