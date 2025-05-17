import tkinter as tk
from tkinter import ttk, messagebox
from threading import Thread
import mpmath
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from methods.integration import METHODS
from utils.functions import FUNCTIONS
from utils.functions import analytical_solution
from utils.runge import compute_integral
from methods.improper import check_singularities
from .plot import create_figure, update_plot

class IntegrationApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Численное интегрирование")
        self.geometry("1000x700")
        self._create_widgets()
        self._setup_layout()
        
    def _create_widgets(self):
        # Левая панель управления
        self.left_frame = ttk.Frame(self, padding=10)
        
        self.func_label = ttk.Label(self.left_frame, text="Выберите функцию:")
        self.func_combo = ttk.Combobox(self.left_frame, 
                                    values=[f"{k}: {v['desc']}" for k, v in FUNCTIONS.items()])
        
        self.method_label = ttk.Label(self.left_frame, text="Метод интегрирования:")
        self.method_combo = ttk.Combobox(self.left_frame, 
                                      values=[f"{k}: {v['name']}" for k, v in METHODS.items()])
        
        self.a_label = ttk.Label(self.left_frame, text="Нижний предел (a):")
        self.a_entry = ttk.Entry(self.left_frame)
        
        self.b_label = ttk.Label(self.left_frame, text="Верхний предел (b):")
        self.b_entry = ttk.Entry(self.left_frame)
        
        self.eps_label = ttk.Label(self.left_frame, text="Точность (ε):")
        self.eps_entry = ttk.Entry(self.left_frame)
        
        self.calc_button = ttk.Button(self.left_frame, text="Вычислить", 
                                    command=self._start_calculation)
        
        # Правая панель с графиком
        self.right_frame = ttk.Frame(self, padding=10)
        self.fig, self.ax = create_figure()
        self.canvas = FigureCanvasTkAgg(self.fig, master=self.right_frame)
        self.canvas.get_tk_widget().pack(fill=tk.BOTH, expand=True)
        
        # Панель результатов
        self.result_text = tk.Text(self.left_frame, height=10, width=40)
        
    def _setup_layout(self):
        self.left_frame.pack(side=tk.LEFT, fill=tk.Y)
        self.right_frame.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True)
        
        widgets = [
            self.func_label, self.func_combo,
            self.method_label, self.method_combo,
            self.a_label, self.a_entry,
            self.b_label, self.b_entry,
            self.eps_label, self.eps_entry,
            self.calc_button,
            self.result_text
        ]
        
        for widget in widgets:
            widget.pack(fill=tk.X, pady=5)
            
        self.canvas.draw()
        
    def _start_calculation(self):
        Thread(target=self._calculate).start()
        
    def _calculate(self):
        try:
            # Получение параметров
            func_num = int(self.func_combo.get().split(":")[0])
            method_num = int(self.method_combo.get().split(":")[0])
            a = mpmath.mpf(self.a_entry.get())
            b = mpmath.mpf(self.b_entry.get())
            eps = mpmath.mpf(self.eps_entry.get())
           
            if func_num in [1,2,7,8]:
                analytical = analytical_solution(func_num, a, b)
                if analytical is not None:
                    print(f"\nАналитическое решение: {mpmath.nstr(analytical, 15)}")

            func_entry = FUNCTIONS[func_num]
            method = METHODS[method_num]
            f = func_entry['func']

            # Обновление графика
            self.ax.clear()
            update_plot(self.ax, f, float(a), float(b))  # Конвертация в float для графика
            self.canvas.draw()

            # Проверка особенностей
            has_sing, loc, sing = check_singularities(func_entry, a, b)
            if has_sing:
                self._handle_singularities(func_entry, a, b, eps, method, loc, sing)
            else:
                integral, n, history = compute_integral(method['func'], f, a, b, eps, 4, method['order'])
                self.result_text.delete(1.0, tk.END)
                self.result_text.insert(tk.END, f"Результат: {mpmath.nstr(integral, 10)}\nРазбиений: {n}")
                
        except Exception as e:
            messagebox.showerror("Ошибка", str(e))
            
    def _handle_singularities(self, func_entry, a, b, eps, method, loc, sing):
        if loc == 'inside':
            point = sing['point']
            messagebox.showerror("Ошибка", 
                f"Интеграл не существует: функция имеет разрыв в точке x = {mpmath.nstr(point, 6)}\n"
                f"на интервале [{mpmath.nstr(a, 3)}, {mpmath.nstr(b, 3)}]")
            return

        converged = sing['converges_at_a'] if loc == 'a' else sing['converges_at_b']
        if not converged:
            messagebox.showerror("Ошибка", "Интеграл расходится")
            return

        # Вычисление несобственного интеграла
        delta = mpmath.mpf('1e-6')
        prev_integral = None
        f = func_entry['func']
        
        try:
            while True:
                if loc == 'a':
                    new_a = a + delta
                    integral, n = compute_integral(method['func'], f, new_a, b, eps, 4, method['order'])
                else:
                    new_b = b - delta
                    integral, n = compute_integral(method['func'], f, a, new_b, eps, 4, method['order'])
                
                if prev_integral and mpmath.fabs(integral - prev_integral) < eps:
                    self.result_text.delete(1.0, tk.END)
                    self.result_text.insert(tk.END, 
                        f"Результат: {mpmath.nstr(integral, 10)}\nDelta: {mpmath.nstr(delta, 2)}")
                    break
                    
                prev_integral = integral
                delta /= 10
                
                if delta < mpmath.mpf('1e-30'):
                    raise ValueError("Не удалось достичь сходимости")
                    
        except Exception as e:
            messagebox.showerror("Ошибка", str(e))
