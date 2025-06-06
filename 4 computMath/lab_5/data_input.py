import numpy as np

def read_from_keyboard():
    raw_x = input("Введите значения x через запятую или пробел: ")
    raw_y = input("Введите значения y через запятую или пробел: ")
    xs = np.fromstring(raw_x.replace(',', ' '), sep=' ')
    ys = np.fromstring(raw_y.replace(',', ' '), sep=' ')
    if xs.size != ys.size:
        raise ValueError(f"Размеры x ({xs.size}) и y ({ys.size}) не совпадают")
    if xs.size < 2:
        raise ValueError("Необходимо не менее двух точек для интерполяции")
    return xs, ys


def read_from_file(filename: str):
    try:
        data = np.loadtxt(filename)
    except Exception as e:
        raise IOError(f"Ошибка чтения файла '{filename}': {e}")
    if data.ndim != 2 or data.shape[1] < 2:
        raise ValueError(f"Файл '{filename}' должен содержать как минимум два столбца")
    xs = data[:, 0]
    ys = data[:, 1]
    if xs.size < 2:
        raise ValueError("В файле должно быть не менее двух точек")
    return xs, ys


def generate_from_function(fun, a: float, b: float, n: int):
    if n < 2:
        raise ValueError("Число точек n должно быть не менее 2")
    if a >= b:
        raise ValueError(f"Неверные границы: a={a} должно быть < b={b}")
    xs = np.linspace(a, b, n)
    ys = fun(xs)
    return xs, ys
