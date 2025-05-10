from decimal import Decimal
from modules.nonlinear_equations import chord_method, secant_method, simple_iteration
from modules.nonlinear_systems import simple_iteration_system
from examples.equations import get_equation, get_system
from modules.utils import (
    decimal_from_str,
    validate_interval,
    save_results,
    load_parameters,
    format_results
)

class CLIInterface:
    """Консольный интерфейс для решения уравнений и систем"""
    
    def __init__(self):
        self.precision = 10
        self.history = []
    
    def run(self):
        """Основной цикл интерфейса"""
        print("Вычислительная математика - Консольный интерфейс")
        while True:
            print("\n1. Решить уравнение")
            print("2. Решить систему уравнений")
            print("3. История вычислений")
            print("4. Выход")
            
            choice = input("Выберите действие: ").strip()
            
            if choice == '1':
                self.solve_equation_menu()
            elif choice == '2':
                self.solve_system_menu()
            elif choice == '3':
                self.show_history()
            elif choice == '4':
                break
            else:
                print("Некорректный выбор")

    def solve_equation_menu(self):
        """Меню решения уравнений"""
        try:
            eq_id = int(input("Введите номер уравнения (1-3): "))
            equation = get_equation(eq_id)
            print(f"\nВыбрано уравнение: {equation['description']}")
            
            method = input("Выберите метод (хорд, секущих, итерация): ").lower()
            epsilon = decimal_from_str(input("Точность (ε): "))
            output_file = input("Файл для сохранения (Enter - пропустить): ")
            
            result = self.solve_equation(equation, method, epsilon)
            self.history.append(result)
            
            print("\nРезультаты:")
            print(format_results(result))
            
            if output_file:
                save_results(output_file, result)
                print(f"Результаты сохранены в {output_file}")
                
        except Exception as e:
            print(f"Ошибка: {str(e)}")

    def solve_equation(self, equation, method, epsilon):
        """Обработчик решения уравнения"""
        if method == 'хорд':
            a = decimal_from_str(input("a: "))
            b = decimal_from_str(input("b: "))
            validate_interval(equation['func'], a, b)
            return chord_method(equation['func'], a, b, epsilon)
        
        elif method == 'секущих':
            x0 = decimal_from_str(input("x₀: "))
            x1 = decimal_from_str(input("x₁: "))
            return secant_method(equation['func'], x0, x1, epsilon)
        
        elif method == 'итерация':
            x0 = decimal_from_str(input("Начальное приближение: "))
            return simple_iteration(equation['func'], equation['phi'], x0, epsilon)
        
        raise ValueError("Неподдерживаемый метод")

    def solve_system_menu(self):
        """Меню решения систем"""
        try:
            sys_id = int(input("Введите номер системы (1-2): "))
            system = get_system(sys_id)
            print("\nВыбрана система:")
            print("\n".join(system['description']))
            
            x0 = decimal_from_str(input("x₀: "))
            y0 = decimal_from_str(input("y₀: "))
            epsilon = decimal_from_str(input("Точность (ε): "))
            
            result = simple_iteration_system(
                system['functions'],
                [x0, y0],
                epsilon
            )
            self.history.append(result)
            
            print("\nРезультаты:")
            print(format_results(result))
            
        except Exception as e:
            print(f"Ошибка: {str(e)}")

    def show_history(self):
        """Показать историю вычислений"""
        print("\nИстория вычислений:")
        for i, result in enumerate(self.history, 1):
            print(f"\nЗапись {i}:")
            print(format_results(result))