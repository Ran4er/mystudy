import numpy as np

def f(x):
    return -0.0811 * x + 1.15

def f2(x):
    return 0.283 + 1.364*x - 0.361*(x**2)

x = np.arange(0, 4.1, 0.4)
y = (24 * x)/(x**4 + 14)
n = len(x)
SX = np.sum(x)
SXX = np.sum(x**2)
SY = np.sum(y)
SXY = np.sum(x*y)
a = (n * SXY - SX * SY) / (n * SXX - SX**2)
b = (SY * SXX - SX * SXY) / (n * SXX - SX**2)
print(f"SX = {SX:.4f}, SXX = {SXX:.4f}, SY = {SY:.4f}, SXY = {SXY:.4f}")
print(f"Аппроксимирующая прямая: y = {a:.4f} * x + {b:.4f}")

sigm_std_d = []

for e in range(len(x)):
    print(f"Значение функции phi(x_{e}): {f(x[e]):.4f}")
    sigm_std_d.append((f(x[e]) - y[e])**2)
    print(f"Значение (phi(x_{e}) - y_{e})^2: {(f(x[e]) - y[e])**2:.4f}")

print(f"Сумма элементов: {np.sum(sigm_std_d):.4f}")
print(f"СКО: {np.std(sigm_std_d):.4f}")

SXXX = np.sum(x**3)
SXXXX = np.sum(x**4)
SXXY = np.sum(x**2 * y)
print(f"SX = {SX:.4f}, SXX = {SXX:.4f}, SXXX = {SXXX:.4f}, SXXXX = {SXXXX:.4f}, SY = {SY:.4f}, SXY = {SXY:.4f}, SXXY = {SXXY:.4f}")

# Coefficient matrix
A = np.array([
    [11, 22, 61.6],
    [22, 61.6, 193.6],
    [61.6, 193.6, 648.525]
])

# Right-hand side vector
B = np.array([10.865, 20.302, 47.198])

# Compute Delta (determinant of A)
delta = np.linalg.det(A)
print(f"Delta = {delta:.6f}")

# Compute Delta_1 (replace 1st column with B)
A1 = A.copy()
A1[:, 0] = B
delta1 = np.linalg.det(A1)
print(f"Delta_1 = {delta1:.6f}")

# Compute Delta_2 (replace 2nd column with B)
A2 = A.copy()
A2[:, 1] = B
delta2 = np.linalg.det(A2)
print(f"Delta_2 = {delta2:.6f}")

# Compute Delta_3 (replace 3rd column with B)
A3 = A.copy()
A3[:, 2] = B
delta3 = np.linalg.det(A3)
print(f"Delta_3 = {delta3:.6f}")

# Solve for a, b, c using Cramer's Rule
if not np.isclose(delta, 0):
    a = delta1 / delta
    b = delta2 / delta
    c = delta3 / delta
    print(f"\nSolution:")
    print(f"a = {a:.6f}")
    print(f"b = {b:.6f}")
    print(f"c = {c:.6f}")
else:
    print("The system has no unique solution (Delta = 0).")

print("\n\n")

sigm_std_d2 = []

for i in range(len(x)):
    print(f"Функция phi(x_{i}): {f2(x[i]):.4f}")
    sigm_std_d2.append((f2(x[i]) - y[i])**2)
    print(f"Разность (дисперсия) (phi(x_{i} - y_{i})^2: {(f2(x[i]) - y[i])**2:.4f}")

print(np.sum(sigm_std_d2))
print(np.std(sigm_std_d2))
