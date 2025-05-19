import numpy as np
from scipy.optimize import curve_fit

def linear_fit(x, y):
    return np.polyfit(x, y, 1)

def poly2_fit(x, y):
    return np.polyfit(x, y, 2)

def poly3_fit(x, y):
    return np.polyfit(x, y, 3)

def exp_fit(x, y):
    return curve_fit(lambda x, a, b: a*np.exp(b*x), x, y, p0=(1, 0.1))[0]

def log_fit(x, y):
    return curve_fit(lambda x, a, b: a*np.log(x)+b, x, y)[0]

def power_fit(x, y):
    return curve_fit(lambda x, a, b: a*(x**b), x, y)[0]

MODELS = [
    {
        'name': 'Линейная',
        'fit_func': linear_fit,
        'predict': lambda x, c: c[0]*x + c[1],
        'requires_positive': False
    },
    {
        'name': 'Полином 2-й степени',
        'fit_func': poly2_fit,
        'predict': lambda x, c: np.polyval(c, x),
        'requires_positive': False
    },
    {
        'name': 'Полином 3-й степени',
        'fit_func': poly3_fit,
        'predict': lambda x, c: np.polyval(c, x),
        'requires_positive': False
    },
    {
        'name': 'Экспоненциальная',
        'fit_func': exp_fit,
        'predict': lambda x, c: c[0]*np.exp(c[1]*x),
        'requires_positive': True
    },
    {
        'name': 'Логарифмическая',
        'fit_func': log_fit,
        'predict': lambda x, c: c[0]*np.log(x) + c[1],
        'requires_positive': True
    },
    {
        'name': 'Степенная',
        'fit_func': power_fit,
        'predict': lambda x, c: c[0]*(x**c[1]),
        'requires_positive': True
    }
]
