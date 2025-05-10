from methods.coordinate_descent import solve as coordinate_descent_solve
from methods.gradient_descent import solve as gradient_descent_solve
from methods.steepest_descent import solve as steepest_descent_solve

def f(x):
    x1, x2 = x
    return 4*x1**2 + 5*x2**2 - 3*x1*x2 + 9*x1 - 2*x2 + 5

def f_grad(x):
    x1, x2 = x
    return [8*x1 - 3*x2 + 9, 10*x2 - 3*x1 - 2]

def print_header(method_name):
    print("\n" + "="*60)
    print(f"Метод {method_name}".center(60))
    print("="*60)

if __name__ == "__main__":
    initial_point = (2, 3)
    epsilon = 0.0001

    methods = {
        "покоординатного спуска": {
            "function": coordinate_descent_solve,
            "derivatives": [f]
        },
        "градиентного спуска": {
            "function": gradient_descent_solve,
            "derivatives": [f, f_grad],
            "params": {  # Дополнительные параметры для метода
                "alpha": 0.1,
                "decay_factor": 0.5,
                "min_alpha": 1e-6,
                "max_iter": 1000
            }
        },
        "наискорейшего спуска": {
            "function": steepest_descent_solve,
            "derivatives": [f, f_grad]
        }
    }

    for name, config in methods.items():
        print_header(name)
        try:
            # Передаем параметры через **
            x_min, f_min = config["function"](
                config["derivatives"],
                initial_point,
                epsilon,
                **config.get("params", {})  # Распаковываем дополнительные параметры
            )
            print(f"\nРезультат: x_min = [{x_min[0]:.5f}, {x_min[1]:.5f}], f(x_min) = {f_min:.5f}")
        except Exception as e:
            print(f"\nОшибка: {str(e)}")