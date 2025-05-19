import mpmath

def check_singularities(func_entry, a, b):
    sings = func_entry.get('singularities', [])
    for sing in sings:
        point = sing['point']

        lower = a if a < b else b
        upper = a if a > b else b

        if lower < point < upper:
            return True, 'inside', sing

        if mpmath.almosteq(point, a, rel_eps=1e-15, abs_eps=1e-15):
            return True, 'a', sing
        if mpmath.almosteq(point, b, rel_eps=1e-15, abs_eps=1e-15):
            return True, 'b', sing
    return False, None, None
