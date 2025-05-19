import numpy as np

def calculate_scores(y_true, y_pred):
    s = np.sum((y_pred - y_true)**2)
    sko = np.sqrt(s / len(y_true))
    ss_tot = np.sum((y_true - np.mean(y_true))**2)
    r2 = 1 - (s / ss_tot) if ss_tot != 0 else 0
    return s, sko, r2

def pearson_coeff(x, y):
    return np.corrcoef(x, y)[0, 1]
