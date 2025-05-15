from constants import A, B, E1, E2
from derivatives import F_DERIVATIES

from methods.newton import solve as solve_via_newton_method
from methods.golden_ratio import solve as solve_via_golden_ratio
from methods.half_division import solve as solve_via_half_division
from methods.chord import solve as solve_via_chord_method
from methods.square_approx import solve as solve_via_quadratic_approx

METHODS = [
    dict(
        name='Вычисление по методу Ньютона',
        func=solve_via_newton_method,
    ),
    dict(
        name='Вычисление по методу половинного деления',
        func=solve_via_half_division
    ),
    dict(
        name='Вычисление по методу золотого сечения',
        func=solve_via_golden_ratio,
    ),
    dict(
        name='Вычисление по методу хорд',
        func=solve_via_chord_method,
    ),
    dict(
        name='Вычисление по методу квадратичной аппроксимации',
        func=solve_via_quadratic_approx,
    )
]


def main():
    for method in METHODS:
        print(f'===========\n\n{method["name"]}:\n')

        solve = method['func']
        x_m, y_m = solve(F_DERIVATIES, A, B, E1, E2)

        print(f'x_m = {x_m}')
        print(f'y_m = f(x_m) = {y_m}')
        print()

if __name__ == '__main__':
    main()