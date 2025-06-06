import tkinter as tk
from tkinter import ttk, messagebox, filedialog
import numpy as np
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import matplotlib.pyplot as plt

import data_input
import differences
import interpolation

class InterpolationApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title('InterpolationApp')
        self.geometry('900x600')

        self.mode_var = tk.IntVar(value=1)
        self.filename_var = tk.StringVar()
        self.fun_var = tk.StringVar(value='sin')
        self.a_var = tk.StringVar()
        self.b_var = tk.StringVar()
        self.n_var = tk.StringVar()
        self.xs_var = tk.StringVar()
        self.ys_var = tk.StringVar()
        self.xq_var = tk.StringVar()

        modes_frame = ttk.LabelFrame(self, text='Режим ввода данных')
        modes_frame.pack(fill='x', padx=10, pady=5)
        ttk.Radiobutton(modes_frame, text='Клавиатура', variable=self.mode_var, value=1, command=self._update_mode).pack(side='left')
        ttk.Radiobutton(modes_frame, text='Файл', variable=self.mode_var, value=2, command=self._update_mode).pack(side='left')
        ttk.Radiobutton(modes_frame, text='Функция', variable=self.mode_var, value=3, command=self._update_mode).pack(side='left')

        params_frame = ttk.Frame(self)
        params_frame.pack(fill='x', padx=10)

        self.kb_frame = ttk.Frame(params_frame)
        ttk.Label(self.kb_frame, text='x (через запятую):').grid(row=0, column=0, sticky='w')
        ttk.Entry(self.kb_frame, textvariable=self.xs_var, width=50).grid(row=0, column=1)
        ttk.Label(self.kb_frame, text='y (через запятую):').grid(row=1, column=0, sticky='w')
        ttk.Entry(self.kb_frame, textvariable=self.ys_var, width=50).grid(row=1, column=1)

        self.file_frame = ttk.Frame(params_frame)
        ttk.Button(self.file_frame, text='Выбрать файл...', command=self._select_file).grid(row=0, column=0, sticky='w')
        ttk.Label(self.file_frame, textvariable=self.filename_var).grid(row=0, column=1, sticky='w')

        self.fun_frame = ttk.Frame(params_frame)
        ttk.Label(self.fun_frame, text='Функции:').grid(row=0, column=0, sticky='w')
        ttk.Combobox(self.fun_frame, textvariable=self.fun_var, values=['sin', 'exp'], width=10).grid(row=0, column=1)
        ttk.Label(self.fun_frame, text='a:').grid(row=0, column=2)
        ttk.Entry(self.fun_frame, textvariable=self.a_var, width=10).grid(row=0, column=3)
        ttk.Label(self.fun_frame, text='b:').grid(row=0, column=4)
        ttk.Entry(self.fun_frame, textvariable=self.b_var, width=10).grid(row=0, column=5)
        ttk.Label(self.fun_frame, text='n:').grid(row=0, column=6)
        ttk.Entry(self.fun_frame, textvariable=self.n_var, width=5).grid(row=0, column=7)

        self._update_mode()

        query_frame = ttk.Frame(self)
        query_frame.pack(fill='x', padx=10, pady=5)
        ttk.Label(query_frame, text='Аргумент x для интерполяции:').pack(side='left')
        ttk.Entry(query_frame, textvariable=self.xq_var, width=10).pack(side='left')
        ttk.Button(query_frame, text='Вычислить и построить', command=self.on_run).pack(side='left', padx=10)

        results = ttk.Panedwindow(self, orient='horizontal')
        results.pack(fill='both', expand=True, padx=10, pady=5)

        table_frame = ttk.Labelframe(results, text='Таблица конечных разностей')
        self.text = tk.Text(table_frame, width=40)
        self.text.pack(fill='both', expand=True)
        results.add(table_frame, weight=1)

        plot_frame = ttk.Labelframe(results, text='График')
        self.fig, self.ax = plt.subplots(figsize=(5,4))
        self.canvas = FigureCanvasTkAgg(self.fig, master=plot_frame)
        self.canvas.get_tk_widget().pack(fill='both', expand=True)
        results.add(plot_frame, weight=3)

    def _update_mode(self):
        for f in (self.kb_frame, self.file_frame, self.fun_frame):
            f.pack_forget()
        mode = self.mode_var.get()
        if mode == 1:
            self.kb_frame.pack(fill='x', pady=2)
        elif mode == 2:
            self.file_frame.pack(fill='x', pady=2)
        else:
            self.fun_frame.pack(fill='x', pady=2)

    def _select_file(self):
        path = filedialog.askopenfilename(initialdir='.', filetypes=[('Text files', '*.txt'), ('All files', '*.*')])
        if path:
            rel = path
            try:
                import os
                rel = os.path.relpath(path, os.getcwd())
            except:
                pass
            self.filename_var.set(rel)

    def on_run(self):
        try:
            mode = self.mode_var.get()
            if mode == 1:
                xs = np.fromstring(self.xs_var.get().replace(',', ' '), sep=' ')
                ys = np.fromstring(self.ys_var.get().replace(',', ' '), sep=' ')
            elif mode == 2:
                xs, ys = data_input.read_from_file(self.filename_var.get())
            else:
                fun = np.sin if self.fun_var.get() == 'sin' else np.exp
                a, b, n = float(self.a_var.get()), float(self.b_var.get()), int(self.n_var.get())
                xs, ys = data_input.generate_from_function(fun, a, b, n)

            diff_table = differences.finite_differences(xs, ys)
            self.text.delete('1.0', tk.END)
            for i, row in enumerate(diff_table):
                vals = [float(v) for v in row]
                row_str = ', '.join(f"{v:.6f}" for v in vals)
                self.text.insert(tk.END, f"Δ^{i}: [{row_str}]\n")

            xq = float(self.xq_var.get())
            yL = interpolation.lagrange_interp(xs, ys, xq)
            yN = interpolation.newton_interp(xs, diff_table, xq)

            try:
                yG = interpolation.gauss_interp(xs, ys, xq)
                gauss_ok = True
            except Exception as ge:
                gauss_ok = False
                gauss_err = str(ge)

            self.text.insert(tk.END, f"\nLagrange: {yL:.6f}\n")
            self.text.insert(tk.END, f"Newton:   {yN:.6f}\n")
            if gauss_ok:
                self.text.insert(tk.END, f"Gauss:    {yG:.6f}\n")
            else:
                self.text.insert(tk.END, f"Gauss:    Ошибка ({gauss_err})\n")

            self.ax.clear()
            fine_x = np.linspace(min(xs), max(xs), 500)

            if mode == 3:
                fun = np.sin if self.fun_var.get() == 'sin' else np.exp
                self.ax.plot(fine_x, fun(fine_x), label='Исходная f(x)')

            self.ax.plot(fine_x, [interpolation.lagrange_interp(xs, ys, xi) for xi in fine_x],
                         label='Lagrange')
            self.ax.plot(fine_x, [interpolation.newton_interp(xs, diff_table, xi) for xi in fine_x],
                         label='Newton')

            if gauss_ok:
                self.ax.plot(fine_x, [interpolation.gauss_interp(xs, ys, xi) for xi in fine_x],
                             label='Gauss')

            self.ax.scatter(xs, ys, label='Узлы', zorder=5)
            self.ax.scatter([xq], [yL], color='black', label='Интерп. точка', zorder=6)

            self.ax.set_xlabel('x')
            self.ax.set_ylabel('y')
            self.ax.set_title('Интерполяция')
            self.ax.legend()
            self.ax.grid(True)
            self.canvas.draw()

        except Exception as e:
            messagebox.showerror('Ошибка', str(e))

    def run(self):
        self.mainloop()
