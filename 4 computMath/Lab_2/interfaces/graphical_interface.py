import tkinter as tk
from tkinter import ttk, messagebox, scrolledtext, filedialog
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
from decimal import Decimal, InvalidOperation
import math

from mpmath import mpf

from modules.nonlinear_equations import (
    chord_method, 
    secant_method, 
    simple_iteration
)
from modules.nonlinear_systems import simple_iteration_system
from examples.equations import get_equation, get_system

class MathApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Решение нелинейных уравнений и систем")
        self.geometry("900x720")

        # Основной контейнер с прокруткой
        self.main_frame = ttk.Frame(self)
        self.main_frame.pack(fill="both", expand=True)

        # Создание Canvas и Scrollbar
        self.canvas = tk.Canvas(self.main_frame)
        self.scrollbar = ttk.Scrollbar(self.main_frame, orient="vertical", command=self.canvas.yview)
        self.scrollable_frame = ttk.Frame(self.canvas)

        # Настройка прокрутки
        self.scrollable_frame.bind(
            "<Configure>",
            lambda e: self.canvas.configure(scrollregion=self.canvas.bbox("all")))
        self.canvas.create_window((0, 0), window=self.scrollable_frame, anchor="nw")
        self.canvas.configure(yscrollcommand=self.scrollbar.set)

        self.canvas.pack(side="left", fill="both", expand=True)
        self.scrollbar.pack(side="right", fill="y")

        # Инициализация вкладок внутри прокручиваемой области
        self.notebook = ttk.Notebook(self.scrollable_frame)
        self.eq_tab = ttk.Frame(self.notebook)
        self.sys_tab = ttk.Frame(self.notebook)
        self.notebook.add(self.eq_tab, text="Уравнения")
        self.notebook.add(self.sys_tab, text="Системы")
        self.notebook.pack(expand=1, fill="both")
        
        # Инициализация вкладок
        self.init_equation_tab()
        self.init_system_tab()
        
        # Конфигурация стилей
        self.style = ttk.Style()
        self.style.configure("TFrame", padding=10)
        self.style.configure("TButton", padding=5)
        
    def init_equation_tab(self):
        """Инициализация вкладки для уравнений"""
        # Выбор уравнения
        eq_frame = ttk.LabelFrame(self.eq_tab, text="Выбор уравнения")
        eq_frame.pack(fill="x", padx=10, pady=5)
        
        self.eq_combobox = ttk.Combobox(eq_frame, values=[
            "x² - 2 = 0", 
            "eˣ - 3x = 0", 
            "sin(x) + x/5 = 0",
            "sin(x) = 0"
        ])
        self.eq_combobox.current(0)
        self.eq_combobox.pack(side="left", padx=5)
        
        # Параметры решения
        param_frame = ttk.LabelFrame(self.eq_tab, text="Параметры")
        param_frame.pack(fill="x", padx=10, pady=5)
        
        ttk.Label(param_frame, text="Метод:").grid(row=0, column=0)
        self.method_var = tk.StringVar()
        methods = ["Хорд", "Секущих", "Простой итерации"]
        self.method_combobox = ttk.Combobox(
            param_frame, 
            textvariable=self.method_var, 
            values=methods
        )
        self.method_combobox.current(0)
        self.method_combobox.grid(row=0, column=1)
        
        # Поля ввода
        input_frame = ttk.Frame(param_frame)
        input_frame.grid(row=1, column=0, columnspan=2, pady=5)
        
        self.a_entry = self.create_input_field(input_frame, "a:", 0)
        self.b_entry = self.create_input_field(input_frame, "b:", 1)
        self.x0_entry = self.create_input_field(input_frame, "x₀:", 2)
        self.epsilon_entry = self.create_input_field(input_frame, "ε:", 3, "1e-6")
        self.eq_canvas_frame = ttk.Frame(self.eq_tab)
        self.eq_canvas_frame.pack(fill="both", expand=True, padx=10, pady=5)
        
        # Кнопки
        btn_frame = ttk.Frame(self.eq_tab)
        btn_frame.pack(pady=10)
        
        ttk.Button(btn_frame, text="Решить", 
                 command=self.solve_equation).pack(side="left", padx=5)
        ttk.Button(btn_frame, text="Построить график",
                 command=self.plot_equation).pack(side="left", padx=5)
        ttk.Button(btn_frame, text="Сохранить в файл",
                   command=self.save_system_results).pack(side="left", padx=5)
        
        # Вывод результатов
        self.result_text = scrolledtext.ScrolledText(self.eq_tab, height=10)
        self.result_text.pack(fill="both", expand=True, padx=10, pady=5)
        
    def init_system_tab(self):
        """Инициализация вкладки для систем"""
        sys_frame = ttk.LabelFrame(self.sys_tab, text="Выбор системы")
        sys_frame.pack(fill="x", padx=10, pady=5)
        
        self.sys_combobox = ttk.Combobox(sys_frame, values=[
            "3x + y² = 1\nx² + 4y = 2", 
            "2x - cos(x) - y = 0\nsin(x) + 3y = 1"
        ])
        self.sys_combobox.current(0)
        self.sys_combobox.pack(side="left", padx=5)

        init_frame = ttk.LabelFrame(self.sys_tab, text="Начальные приближения")
        init_frame.pack(fill="x", padx=10, pady=5)
        
        self.x0_sys_entry = self.create_input_field(init_frame, "x₀:", 0)
        self.y0_sys_entry = self.create_input_field(init_frame, "y₀:", 1)
        self.epsilon_sys_entry = self.create_input_field(init_frame, "ε:", 2, "1e-6")
        self.sys_canvas_frame = ttk.Frame(self.sys_tab)
        self.sys_canvas_frame.pack(fill="both", expand=True, padx=10, pady=5)
        
        # Кнопки
        btn_frame = ttk.Frame(self.sys_tab)
        btn_frame.pack(pady=10)
        
        ttk.Button(btn_frame, text="Решить", 
                 command=self.solve_system).pack(side="left", padx=5)
        ttk.Button(btn_frame, text="Построить график",
                 command=self.plot_system).pack(side="left", padx=5)
        ttk.Button(btn_frame, text="Сохранить в файл",
                   command=self.save_system_results).pack(side="left", padx=5)

        self.sys_result_text = scrolledtext.ScrolledText(self.sys_tab, height=10)
        self.sys_result_text.pack(fill="both", expand=True, padx=10, pady=5)
        self.a_sys_entry = self.create_input_field(init_frame, "a:", 3)
        self.b_sys_entry = self.create_input_field(init_frame, "b:", 4)
        self.a_sys_entry.insert(0, "-1")
        self.b_sys_entry.insert(0, "1")

    def create_input_field(self, parent, label, row, default=""):
        """Создает поле ввода с меткой"""
        ttk.Label(parent, text=label).grid(row=row, column=0, padx=5)
        entry = ttk.Entry(parent, width=15)
        entry.grid(row=row, column=1, padx=5)
        if default:
            entry.insert(0, default)
        return entry
    
    def solve_equation(self):
        """Обработчик решения уравнения"""
        try:
            eq_id = self.eq_combobox.current() + 1
            method = self.method_var.get()
            epsilon = self.parse_decimal(self.epsilon_entry.get())

            eq = get_equation(eq_id)

            if method == "Хорд":
                a = self.parse_decimal(self.a_entry.get())
                b = self.parse_decimal(self.b_entry.get())
                result = chord_method(eq['func'], a, b, epsilon)
            elif method == "Секущих":
                x0 = self.parse_decimal(self.x0_entry.get())
                x1 = self.parse_decimal(self.b_entry.get())
                result = secant_method(eq['func'], x0, x1, epsilon)
            elif method == "Простой итерации":
                x0 = self.parse_decimal(self.x0_entry.get())
                result = simple_iteration(
                    eq['func'], eq['phi'], x0, epsilon
                )
            else:
                raise ValueError("Неизвестный метод")

            self.show_result(result, eq['description'])
            
        except Exception as e:
            messagebox.showerror("Ошибка", str(e))

    def solve_system(self):
        """Обработчик решения системы"""
        try:
            sys_id = self.sys_combobox.current() + 1
            x0 = mpf(str(self.x0_sys_entry.get()))
            y0 = mpf(str(self.y0_sys_entry.get()))
            epsilon = mpf(str(self.epsilon_sys_entry.get()))

            system = get_system(sys_id)
            result = simple_iteration_system(
                system['functions'],
                [x0, y0],
                epsilon
            )

            if 'error' in result:
                raise ValueError(result['error'])

            x, y = result['solution']
            text = f"Решение: ({x}, {y})\n"
            text += f"Итераций: {result['iterations']}\n"
            text += f"Ошибка: {result['errors'][-1] if result['errors'] else 'Нет данных'}"

            self.sys_result_text.delete(1.0, tk.END)
            self.sys_result_text.insert(tk.END, text)

        except Exception as e:
            self.sys_result_text.delete(1.0, tk.END)
            self.sys_result_text.insert(tk.END, f"Ошибка: {str(e)}")

    def plot_equation(self):
        """Построение графика уравнения с решением и масштабированием"""
        try:
            eq_id = self.eq_combobox.current() + 1
            eq = get_equation(eq_id)
            a = float(self.a_entry.get())
            b = float(self.b_entry.get())

            fig = Figure(figsize=(6, 4))
            ax = fig.add_subplot(111)

            x = [a + (b - a) * i / 100 for i in range(100)]
            y = [eq['func_float'](xi) for xi in x]

            ax.plot(x, y, label=eq['description'])
            ax.axhline(0, color='black', linewidth=0.5)
            ax.axvline(0, color='black', linewidth=0.5)

            result_text = self.result_text.get("1.0", tk.END)
            if "Корень:" in result_text:
                root = float(result_text.split("Корень: ")[1].split("\n")[0])
                f_root = eq['func_float'](root)
                ax.plot(root, f_root, 'ro', markersize=8, label='Решение')

            x = [a + (b - a) * i / 1000 for i in range(1000)]
            y = [eq['func_float'](xi) for xi in x]
            ax.set_xlim(a, b)
            ax.set_ylim(min(y) - 0.5, max(y) + 0.5)
            if ax.get_legend_handles_labels()[1]:
                ax.legend()
            ax.grid(True)
            self.show_figure(fig)

        except Exception as e:
            messagebox.showerror("Ошибка", str(e))

    def plot_system(self):
        """Построение графиков системы уравнений с решением и оформлением"""
        try:
            import numpy as np
            sys_id = self.sys_combobox.current() + 1
            system = get_system(sys_id)

            a = float(self.a_sys_entry.get())
            b = float(self.b_sys_entry.get())
            if a >= b:
                raise ValueError("Интервал задан некорректно: a >= b")

            result_text = self.sys_result_text.get("1.0", tk.END)
            solution = None
            if "Решение:" in result_text:
                solution_str = result_text.split("Решение: ")[1].split("\n")[0]
                try:
                    solution = [
                        float(num.strip().strip("'"))
                        for num in solution_str.strip('()').split(',')
                    ]
                except Exception as e:
                    print(f"Ошибка преобразования: {e}")
                    solution = None

            x = np.linspace(a, b, 400)
            y = np.linspace(a, b, 400)
            X, Y = np.meshgrid(x, y)

            fig = Figure(figsize=(8, 6))
            ax = fig.add_subplot(111)

            if sys_id == 1:
                F1 = 3 * X + Y ** 2 - 1
                F2 = X ** 2 + 4 * Y - 2
            elif sys_id == 2:
                F1 = 2 * X - np.cos(X) - Y
                F2 = np.sin(X) + 3 * Y - 1

            ax.contour(X, Y, F1, levels=[0], colors='blue', linewidths=1)
            ax.contour(X, Y, F2, levels=[0], colors='green', linewidths=1)

            # Отображение красной точки решения
            if solution and len(solution) == 2:
                x_sol, y_sol = solution
                ax.plot(x_sol, y_sol, 'ro', markersize=8, label='Решение', zorder=5)
                ax.set_xlim(a, b)
                ax.set_ylim(a, b)
                print(f"Решение отображено: ({x_sol}, {y_sol})")

            ax.set_title("\n".join(system['description']))
            ax.grid(True, linestyle='--', alpha=0.7)
            ax.axhline(0, color='black', lw=0.5)
            ax.axvline(0, color='black', lw=0.5)
            if ax.get_legend_handles_labels()[1]:
                ax.legend()
            self.show_figure(fig, is_system=True)

        except Exception as e:
            messagebox.showerror("Ошибка отрисовки", f"Не удалось построить график:\n{str(e)}")

    def save_system_results(self):
        """Сохранение результатов системы в файл"""
        result_text = self.sys_result_text.get("1.0", tk.END)
        if not result_text.strip():
            messagebox.showwarning("Пустые данные", "Нет результатов для сохранения")
            return

        filepath = filedialog.asksaveasfilename(
            defaultextension=".txt",
            filetypes=[("Text files", "*.txt"), ("All files", "*.*")]
        )
        if not filepath:
            return

        try:
            with open(filepath, "w") as f:
                f.write(result_text)
            messagebox.showinfo("Успех", "Файл сохранен")
        except Exception as e:
            messagebox.showerror("Ошибка", f"Не удалось сохранить файл: {str(e)}")

    def parse_decimal(self, value):
        """Преобразует строку в Decimal с обработкой ошибок"""
        try:
            return Decimal(value.replace(',', '.'))
        except InvalidOperation:
            raise ValueError("Некорректное числовое значение")
    
    def show_result(self, result, equation):
        """Отображает результаты решения уравнения"""
        text = f"Уравнение: {equation}\n"
        if 'error' in result:
            text += f"Ошибка: {result['error']}"
        else:
            text += f"Корень: {result['root']}\n"
            text += f"Значение функции: {result['f_root']}\n"
            text += f"Итераций: {result['iterations']}"
        
        self.result_text.delete(1.0, tk.END)
        self.result_text.insert(tk.END, text)
    
    '''def show_figure(self, figure):
        """Отображает график в новом окне"""
        top = tk.Toplevel(self)
        canvas = FigureCanvasTkAgg(figure, master=top)
        canvas.draw()
        canvas.get_tk_widget().pack(fill="both", expand=True)'''

    def show_figure(self, figure, is_system=False):
        """Отображает график в текущей вкладке"""
        frame = self.sys_canvas_frame if is_system else self.eq_canvas_frame
        for widget in frame.winfo_children():
            widget.destroy()
        canvas = FigureCanvasTkAgg(figure, master=frame)
        canvas.draw()
        canvas.get_tk_widget().pack(fill="both", expand=True)

if __name__ == "__main__":
    app = MathApp()
    app.mainloop()