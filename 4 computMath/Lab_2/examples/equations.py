import math
from decimal import Decimal
from typing import Dict, Callable, List
from mpmath import mp, sin, cos, exp, mpf

#Setting up quality
mp.dps = 50

class Equation:
    def __init__(self, func: Callable, description: str, phi: Callable = None, func_float: Callable = None):
        self.func = func
        self.description = description
        self.phi = phi
        self.func_float = func_float


nonlinear_equations = {
    1: Equation(
        func=lambda x: x**2 - Decimal('2'),
        func_float=lambda x: x**2 - 2,
        description="x² - 2 = 0",
        phi=lambda x: (x + Decimal('2') / x) / Decimal('2')
    ),
    2: Equation(
        func=lambda x: exp(x) - Decimal('3') * x,
        func_float=lambda x: math.exp(x) - 3 * x,
        description="eˣ - 3x = 0",
        phi=lambda x: exp(x) / Decimal('3')
    ),
    3: Equation(
        func=lambda x: sin(x) + x / Decimal('5'),
        func_float=lambda x: math.sin(x) + x / 5,
        description="sin(x) + x/5 = 0",
        phi=lambda x: -sin(x) * Decimal('5')
    ),
    4: Equation(
        func=lambda x: sin(x),
        func_float=lambda x: math.sin(x),
        description="sin(x) = 0",
        phi=lambda x: x - sin(x)
    )
}

nonlinear_systems = {
    1: {
        'functions': [
            lambda x,y: (1 - y**2)/3,
            lambda x,y: (2 - x**2)/4
        ],
        'description': [
            "3x + y² = 1",
            "x² + 4y = 2"
        ],
        'partial_derivatives': [
            [lambda x,y: Decimal('0'), lambda x,y: (Decimal('2')*y)/Decimal('3')],
            [lambda x,y: (Decimal('2')*x)/Decimal('4'), lambda x,y: Decimal('0')]
        ],
        'domain': [
            (Decimal('-2'), Decimal('2')),
            (Decimal('-2'), Decimal('2'))
        ]
    },
    2: {
        'functions': [
            lambda x,y: (cos(mpf(str(x))) + mpf(str(y))) / 2,
            lambda x,y: (1 - sin(mpf(str(x)))) / 3
        ],
        'description': [
            "2x - cos(x) - y = 0",
            "sin(x) + 3y = 1"
        ],
        'partial_derivatives': [
            [lambda x, y: (-sin(x)) / Decimal('2'), lambda x, y: Decimal('0.5')],
            [lambda x, y: cos(x) / Decimal('3'), lambda x, y: Decimal('0')]
        ],
        'domain': [
            (Decimal('0'), Decimal('1')),
            (Decimal('0'), Decimal('1'))
        ]
    }
}

def get_equation(id: int) -> Dict:
    eq = nonlinear_equations[id]
    return {
        'func': eq.func,
        'func_float': eq.func_float,
        'description': eq.description,
        'phi': eq.phi
    }


def get_system(id: int) -> Dict:
    return nonlinear_systems[id]