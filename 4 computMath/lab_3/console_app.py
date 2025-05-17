from methods.integration import METHODS
from methods.improper import check_singularities
from utils.functions import FUNCTIONS
from utils.fucntions import analytical_solution
from utils.runge import compute_integral
import mpmath

def get_user_input():
    print("\nДоступные функции:")
    for num, func in FUNCTIONS.items():
        print(f"{num}: {func['desc']}")
    
    func_choice = int(input("Выберите номер функции: "))
    a = mpmath.mpf(input("Введите нижний предел a: "))
    b = mpmath.mpf(input("Введите верхний предел b: "))
    epsilon = mpmath.mpf(input("Введите точность ε: "))
    
    print("\nДоступные методы:")
    for num, method in METHODS.items():
        print(f"{num}: {method['name']}")
    method_choice = int(input("Выберите метод: "))
    
    return {
        'func_num': func_choice,
        'a': a,
        'b': b,
        'epsilon': epsilon,
        'method_num': method_choice
    }

def handle_singularity(loc, sing, a, b):
    point = sing['point']
    if loc == 'inside':
        print(f"\nОшибка: Интеграл не существует, так как функция имеет разрыв в точке x = {mpmath.nstr(point, 6)} внутри интервала [{mpmath.nstr(a, 3)}, {mpmath.nstr(b, 3)}]")
        return True
    return False

def console_main():
    while True:
        try:
            params = get_user_input()
            func_num = params['func_num']
            method_num = params['method_num']
            a = params['a']
            b = params['b']
            epsilon = params['epsilon']
           
            if func_num in [1,2,7,8]:
                analytical = analytical_solution(func_num, a, b)
                if analytical is not None:
                    print(f"\nАналитическое решение: {mpmath.nstr(analytical, 15)}")

            func_entry = FUNCTIONS[func_num]
            method = METHODS[method_num]
            f = func_entry['func']
            
            has_sing, loc, sing = check_singularities(func_entry, a, b)
            if has_sing:
                if handle_singularity(loc, sing, a, b):
                    continue
                
                converged = sing['converges_at_a'] if loc == 'a' else sing['converges_at_b']
                if not converged:
                    point = sing['point']
                    print(f"\nОшибка: Интеграл расходится, функция имеет неустранимый разрыв в точке x = {mpmath.nstr(point, 6)}")
                    continue
                
                # Вычисление несобственного интеграла
                delta = mpmath.mpf('1e-6')
                prev_integral = None
                for _ in range(100):
                    if loc == 'a':
                        new_a = a + delta
                        integral, n = compute_integral(method['func'], f, new_a, b, epsilon, 4, method['order'])
                    else:
                        new_b = b - delta
                        integral, n = compute_integral(method['func'], f, a, new_b, epsilon, 4, method['order'])
                    
                    if prev_integral and mpmath.fabs(integral - prev_integral) < epsilon:
                        print(f"\nРезультат (несобственный интеграл): {mpmath.nstr(integral, 10)}")
                        print(f"Использовано разбиений: {n}, delta: {mpmath.nstr(delta, 2)}")
                        break
                        
                    prev_integral = integral
                    delta /= 10
                else:
                    print("Не удалось достичь требуемой точности")
                continue
            
            integral, n = compute_integral(method['func'], f, a, b, epsilon, 4, method['order'])
            print(f"\nРезультат: {mpmath.nstr(integral, 10)}")
            print(f"Использовано разбиений: {n}")
            
        except Exception as e:
            print(f"\nОшибка: {str(e)}")
        
        if input("\nПродолжить? (y/n): ").lower() != 'y':
            break

if __name__ == "__main__":
    console_main()
