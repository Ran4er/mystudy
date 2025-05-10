from decimal import Decimal, InvalidOperation
from typing import Union, List, Dict, Callable
import csv
import json
import os

def decimal_from_str(value: str) -> Decimal:
    """Безопасное преобразование строки в Decimal"""
    try:
        return Decimal(value.replace(',', '.'))
    except InvalidOperation as e:
        raise ValueError(f"Некорректное числовое значение: {value}") from e

def validate_interval(f: Callable[[Decimal], Decimal], 
                     a: Decimal, 
                     b: Decimal) -> None:
    """Проверка наличия корня на интервале"""
    if a >= b:
        raise ValueError("Интервал задан некорректно (a >= b)")
    
    fa, fb = f(a), f(b)
    if fa * fb >= 0:
        raise ValueError("На интервале нет корней или несколько корней")

def save_results(filename: str, 
                results: Union[Dict, List[Dict]], 
                file_type: str = 'json') -> None:
    """Сохранение результатов в файл"""
    try:
        with open(filename, 'w', encoding='utf-8') as f:
            if file_type == 'json':
                json.dump(results, f, indent=4, ensure_ascii=False)
            elif file_type == 'csv':
                writer = csv.writer(f)
                if isinstance(results, list):
                    writer.writerow(results[0].keys())
                    for row in results:
                        writer.writerow(row.values())
                else:
                    writer.writerow(results.keys())
                    writer.writerow(results.values())
            else:
                raise ValueError("Неподдерживаемый формат файла")
    except Exception as e:
        raise IOError(f"Ошибка записи в файл: {str(e)}") from e

def load_parameters(filename: str) -> Dict:
    """Загрузка параметров из файла"""
    ext = os.path.splitext(filename)[1].lower()
    try:
        with open(filename, 'r', encoding='utf-8') as f:
            if ext == '.json':
                return json.load(f)
            elif ext == '.csv':
                reader = csv.DictReader(f)
                return next(reader)
            else:
                raise ValueError("Неподдерживаемый формат файла")
    except Exception as e:
        raise IOError(f"Ошибка чтения файла: {str(e)}") from e

def format_results(results: Dict) -> str:
    """Форматирование результатов для вывода"""
    if 'error' in results:
        return f"Ошибка: {results['error']}"
    
    text = []
    if 'root' in results:
        text.append(f"Корень: {results['root']:.10f}")
        text.append(f"Значение функции: {results['f_root']:.2e}")
    if 'solution' in results:
        text.append(f"Решение: {[round(x, 10) for x in results['solution']]}")
    text.append(f"Итераций: {results['iterations']}")
    
    return "\n".join(text)

def dynamic_precision(precision: int) -> None:
    """Динамическая настройка точности вычислений"""
    from decimal import getcontext
    getcontext().prec = max(30, precision * 2)