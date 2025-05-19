import mpmath

def compute_integral(method_func, f, a, b, epsilon, initial_n, order):
    """
    Вычисляет интеграл с заданной точностью используя правило Рунге.
    
    Параметры:
        method_func: Функция метода интегрирования
        f: Подынтегральная функция
        a: Нижний предел
        b: Верхний предел
        epsilon: Требуемая точность
        initial_n: Начальное число разбиений
        order: Порядок метода
    
    Возвращает:
        integral: Значение интеграла
        n: Число разбиений
    """
    n = initial_n
    max_iterations = 25
    best_integral = mpmath.mpf(0)
    best_error = mpmath.inf
    best_n = initial_n

    for iteration in range(max_iterations):
        try:
            integral_n = method_func(f, a, b, n)
            integral_2n = method_func(f, a, b, 2 * n)
            
            if mpmath.fabs(integral_2n) > 1e-100:
                error = mpmath.fabs((integral_n - integral_2n) / (2**order - 1))
            else:
                error = mpmath.fabs(integral_n - integral_2n)
            
            if error < best_error:
                best_integral = integral_2n
                best_error = error
                best_n = 2 * n

            if error < epsilon:
                return best_integral, best_n
                
            if iteration > 5 and error > best_error * 0.9:
                raise ValueError(f"Погрешность растёт. Текущая: {mpmath.nstr(error, 4)}")

            n *= 2  
        except Exception as e:
            print(str(e))
            break

    # Возвращаем лучший результат даже если не достигли точности
    if best_error < mpmath.inf:
        return best_integral, best_n
    
    raise ValueError(
        f"Не удалось вычислить интеграл. Последняя погрешность: {mpmath.nstr(error, 4)}")
