import numpy as np
from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

class Plotter:
    def __init__(self, parent_frame, width=5, height=4, dpi=100):
        self.fig = Figure(figsize=(width, height), dpi=dpi)
        self.ax = self.fig.add_subplot(111)
        self.canvas = FigureCanvasTkAgg(self.fig, master=parent_frame)
        self.canvas.get_tk_widget().pack(fill='both', expand=True)

    def clear(self):
        self.ax.clear()

    def plot_function(self, fun, a, b, num=500, label='f(x)'):
        xs = np.linspace(a, b, num)
        ys = fun(xs)
        self.ax.plot(xs, ys, label=label)

    def plot_curve(self, xs, interp_func, label):
        xs_fine = np.linspace(np.min(xs), np.max(xs), 500)
        ys_fine = [interp_func(xi) for xi in xs_fine]
        self.ax.plot(xs_fine, ys_fine, label=label)

    def plot_nodes(self, xs, ys, label='Nodes', marker='o'):
        self.ax.scatter(xs, ys, marker=marker, label=label, zorder=5)

    def mark_point(self, x, y, label='Query', color='black'):
        self.ax.scatter([x], [y], color=color, label=label, zorder=6)

    def draw(self, xlabel='x', ylabel='y', title='Interpolation'):
        self.ax.set_xlabel(xlabel)
        self.ax.set_ylabel(ylabel)
        self.ax.set_title(title)
        self.ax.grid(True)
        self.ax.legend()
        self.canvas.draw()
