def compute_finite_differences(y):
    n = len(y)
    differences = [y.copy()]
    for order in range(1, 7):
        current_diff = []
        for i in range(n - order):
            diff = round(differences[order-1][i+1] - differences[order-1][i], 4)
            current_diff.append(diff)
        differences.append(current_diff)
    return differences

# Данные для варианта 14
y = [0.1213, 1.1316, 2.1459, 3.1565, 4.1571, 5.1819, 6.1969]
diffs = compute_finite_differences(y)

# Форматированный вывод
headers = ["№", "x_i", "y_i", "Δy_i", "Δ²y_i", "Δ³y_i", "Δ⁴y_i", "Δ⁵y_i", "Δ⁶y_i"]
print("|".join(headers))
print("-" * 95)
for i in range(len(y)):
    row = [
        str(i),
        f"{1.05 + 0.10*i:.2f}",
        f"{y[i]:.4f}"
    ]
    for order in range(1, 7):
        if order < len(diffs) and i < len(diffs[order]):
            row.append(f"{diffs[order][i]:.4f}")
        else:
            row.append(" ")
    print("|".join(row))

def compute_interpolation():
    # Данные из таблицы и формулы
    y0 = 0.1213
    t = 0.62
    delta_y0 = 1.0103
    delta2_y0 = 0.0040
    delta3_y0 = -0.0053  # В формуле указано -0.0077, но в таблице -0.0053
    delta4_y0 = 0.0139
    delta5_y0 = -0.0275
    delta6_y0 = 0.0429   # Не используется в формуле, но может потребоваться

    # Вычисление слагаемых
    term1 = y0
    term2 = t * delta_y0
    term3 = (t * (t - 1) / 2) * delta2_y0
    term4 = (t * (t - 1) * (t - 2) / 6) * delta3_y0
    term5 = (t * (t - 1) * (t - 2) * (t - 3) / 24) * delta4_y0
    term6 = (t * (t - 1) * (t - 2) * (t - 3) * (t - 4) / 120) * delta5_y0

    # Суммирование всех слагаемых
    result = term1 + term2 + term3 + term4 + term5 + term6
    return round(result, 4)

# Запуск вычислений
interpolated_value = compute_interpolation()
print(f"y(1.112) ≈ {interpolated_value}")

def compute_interpolation_x2():
    # Данные из формулы
    y_base = 3.1565  # y₋₁ (сдвиг на предыдущий узел)
    t = 0.62
    delta_y = 1.0106
    delta2_y = -0.01
    delta3_y = 0.0063
    delta4_y = 0.0405
    delta5_y = 0.0391
    delta6_y = -0.1478

    # Вычисление слагаемых
    term1 = y_base
    term2 = t * delta_y
    term3 = (t * (t - 1) / 2) * delta2_y
    term4 = (t * (t - 1) * (t - 2) / 6) * delta3_y
    term5 = (t * (t - 1) * (t - 2) * (t - 3) / 24) * delta4_y
    term6 = (t * (t - 1) * (t - 2) * (t - 3) * (t - 4) / 120 * delta5_y)
    term7 = (t * (t - 1) * (t - 2) * (t - 3) * (t - 4) * (t - 5) / 720 * delta6_y)

    # Суммирование всех слагаемых
    result = term1 + term2 + term3 + term4 + term5 + term6 + term7
    return round(result, 4)

# Запуск вычислений
interpolated_value_x2 = compute_interpolation_x2()
print(f"y(1.319) ≈ {interpolated_value_x2}")