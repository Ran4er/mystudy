import mpmath

mpmath.mp.dps = 15

FUNCTIONS = {
    1: {
        'func': lambda x: mpmath.power(x, 2),
        'desc': 'x²',
        'singularities': []
    },
    2: {
        'func': lambda x: mpmath.sin(x),
        'desc': 'sin(x)',
        'singularities': []
    },
    3: {
        'func': lambda x: 1/mpmath.sqrt(x) if x > 0 else mpmath.inf,
        'desc': '1/√x (особенность в x=0)',
        'singularities': [{
            'point': mpmath.mpf(0),
            'converges_at_a': True, 
            'converges_at_b': False
        }]
    },
    4: {
        'func': lambda x: 1/x if x != 0 else mpmath.inf,
        'desc': '1/x (особенность в x=0)',
        'singularities': [{
            'point': mpmath.mpf(0),
            'converges_at_a': False, 
            'converges_at_b': False
        }]
    },
    5: {
        'func': lambda x: 1/mpmath.sqrt(x-2) if x > 2 else mpmath.inf,
        'desc': '1/√(x-2) (особенность в x=2)',
        'singularities': [{
            'point': mpmath.mpf(2),
            'converges_at_a': False,
            'converges_at_b': True 
        }]
    },
    6: {
        'func': lambda x: 1/mpmath.power(x-1, 2) if x != 1 else mpmath.inf,
        'desc': '1/(x-1)² (особенность в x=1)',
        'singularities': [{
            'point': mpmath.mpf(1),
            'converges_at_a': False, 
            'converges_at_b': False
        }]
    },
    7: {
        'func': lambda x: mpmath.log(x) if x > 0 else mpmath.inf,
        'desc': 'ln(x) (особенность в x=0)',
        'singularities': [{
            'point': mpmath.mpf(0),
            'converges_at_a': True, 
            'converges_at_b': False
        }]
    },
    8: {
        'func': lambda x: mpmath.sqrt(4 - mpmath.power(x, 2)),
        'desc': '√(4-x²) (определена на [-2, 2])',
        'singularities': [
            {
                'point': mpmath.mpf(-2),
                'converges_at_a': True, 
                'converges_at_b': True
            },
            {
                'point': mpmath.mpf(2),
                'converges_at_a': True,
                'converges_at_b': True
            }
        ]
    },
    9: {
        'func': lambda x: 1/mpmath.power(x, 3) if x != 0 else mpmath.inf,
        'desc': '1/x³ (особенность в x=0)',
        'singularities': [{
            'point': mpmath.mpf(0),
            'converges_at_a': False,  
            'converges_at_b': False
        }]
    }
}

def analytical_solution(func_num, a, b):
    solutions = {
        1: lambda a, b: (mpmath.power(b,3)/3 - mpmath.power(a,3)/3),
        2: lambda a, b: mpmath.cos(a) - mpmath.cos(b),
        7: lambda a, b: b*mpmath.log(b) - b - (a*mpmath.log(a) - a),
        8: lambda a, b: (
            (b/2*mpmath.sqrt(4 - b**2) + 2*mpmath.asin(b/2)) - 
            (a/2*mpmath.sqrt(4 - a**2) + 2*mpmath.asin(a/2))
        )
    }
    return solutions.get(func_num, None)
