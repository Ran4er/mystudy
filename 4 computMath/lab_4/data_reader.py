import numpy as np

def read_data():
    """Чтение данных из файла или консоли"""
    source = input("Введите путь к файлу или 'n', чтобы ввести данные вручную: ")
    x, y = [], []
    
    if source.lower() != 'n':
        try:
            with open(source, 'r') as f:
                data = f.readlines()
            for line in data:
                parts = line.strip().split()
                if len(parts) >= 2:
                    x.append(float(parts[0]))
                    y.append(float(parts[1]))
        except Exception as e:
            print(f"Ошибка чтения файла: {e}")
            return None, None
    else:
        n = int(input("Введите количество точек (8-12): "))
        if n < 8 or n > 12:
            print("Ошибка: требуется 8-12 точек.")
            return None, None
        for i in range(n):
            x.append(float(input(f"x[{i}]: ")))
            y.append(float(input(f"y[{i}]: ")))
    
    x = np.array(x)
    y = np.array(y)
    
    if len(x) < 8 or len(x) > 12:
        print("Ошибка: неверное количество точек.")
        return None, None
    
    return x, y
