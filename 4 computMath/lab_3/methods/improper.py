import mpmath

def check_singularities(func_entry, a, b):
    sings = func_entry.get('singularities', [])
    for sing in sings:
        point = sing['point']
        if mpmath.fabs(point - a) < 1e-12:
            return True, 'a', sing
        elif mpmath.fabs(point - b) < 1e-12:
            return True, 'b', sing
        elif a < point < b:
            return True, 'inside', sing
    return False, None, None
