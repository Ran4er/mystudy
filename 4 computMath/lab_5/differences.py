def finite_differences(xs, ys):
    n = len(xs)
    if n < 2:
        raise ValueError("Нужно минимум две точки для вычисления разностей")
    if len(ys) != n:
        raise ValueError(f"Длины xs ({n}) и ys ({len(ys)}) не совпадают")

    diff = [list(ys)]
    for level in range(1, n):
        prev = diff[level-1]
        curr = []
        for i in range(n - level):
            numerator = prev[i+1] - prev[i]
            denominator = xs[i+level] - xs[i]
            if denominator == 0:
                raise ZeroDivisionError(f"Повторяющиеся узлы xs[{i}] = xs[{i+level}] = {xs[i]}")
            curr.append(numerator / denominator)
        diff.append(curr)
    return diff
