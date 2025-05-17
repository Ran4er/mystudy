import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

def create_figure():
    fig = plt.figure(figsize=(6, 4))
    ax = fig.add_subplot(111)
    return fig, ax

def update_plot(ax, f, a, b):
    ax.clear()
    x = [a + (b - a)*i/100 for i in range(101)]
    y = [f(xi) if abs(f(xi)) < 1e6 else float('nan') for xi in x]
    ax.plot(x, y, 'b-')
    ax.set_title("График функции")
    ax.grid(True)
    ax.set_xlabel('x')
    ax.set_ylabel('f(x)')
