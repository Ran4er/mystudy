"""
Градиентный спуск
"""

def solve(
    f_derivatives, 
    initial_point, 
    epsilon, 
    alpha=0.5,
    decay_factor=2,
    min_alpha=1e-10, 
    max_iter=1000
):
    x = list(initial_point)
    iteration = 0
    prev_f = f_derivatives[0](x)
    
    while iteration < max_iter:
        grad = f_derivatives[1](x)
        current_alpha = alpha
        success = False
        
        # Ищем подходящий шаг alpha
        while current_alpha > min_alpha:
            new_x = [x[i] - current_alpha * grad[i] for i in range(len(x))]
            new_f = f_derivatives[0](new_x)
            
            # Проверка уменьшения функции
            if abs(new_f - prev_f) > epsilon:
                x = new_x
                prev_f = new_f
                success = True
                break
                
            current_alpha //= decay_factor
        
        # Если подходящий шаг не найден
        if not success:
            break
            
        # Проверка сходимости по норме градиента
        if all(abs(g) < epsilon for g in grad):
            break
            
        iteration += 1
    
    return x, prev_f