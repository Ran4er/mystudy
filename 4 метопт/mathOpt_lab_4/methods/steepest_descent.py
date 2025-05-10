"""
Наискорейший спуск
"""

from methods.golden_section import solve as golden_section_solve

def solve(f_derivatives, initial_point, epsilon, max_iter=1000):
    x = list(initial_point)  # Начальная точка
    iteration = 0

    while iteration < max_iter:
        grad = f_derivatives[1](x)  # Вычисление градиента

        # Функция для минимизации вдоль направления антиградиента
        def func(alpha):
            new_x = [x[i] - alpha * grad[i] for i in range(len(x))]
            return f_derivatives[0](new_x)

        # Поиск оптимального шага alpha методом золотого сечения
        alpha_opt, _ = golden_section_solve([func], 0, 1, epsilon)

        # Обновление точки
        new_x = [x[i] - alpha_opt * grad[i] for i in range(len(x))]

        # Проверка условия остановки (изменение координат < epsilon)
        if all(abs(new_x[i] - x[i]) < epsilon for i in range(len(x))):
            break

        x = new_x  # Переход к новой точке
        iteration += 1

    return x, f_derivatives[0](x)  # Возврат результата