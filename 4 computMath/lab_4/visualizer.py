import matplotlib.pyplot as plt
import numpy as np

def plot_results(x, y, results, models):
    plt.figure(figsize=(12, 8))
    plt.scatter(x, y, c='black', label='Данные')
    
    x_range = np.linspace(x.min()-0.1, x.max()+0.1, 500)
    
    for res in results:
        if not res['valid']:
            continue
        model = next(m for m in models if m['name'] == res['name'])
        y_plot = model['predict'](x_range, res['coeffs'])
        plt.plot(x_range, y_plot, label=f"{res['name']} (СКО: {res['sko']:.2f})")
    
    plt.xlabel('x')
    plt.ylabel('y')
    plt.legend()
    plt.grid()
    plt.show()
