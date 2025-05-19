import numpy as np
from data_reader import read_data
from models import MODELS
from metrics import calculate_scores, pearson_coeff
from visualizer import plot_results

def main():
    x, y = read_data()
    if x is None:
        return

    results = []
    
    for model in MODELS:
        result = {'name': model['name'], 'valid': False}
        
        # Проверка на положительные значения
        if model['requires_positive'] and (np.any(x <= 0) or np.any(y <= 0)):
            result['error'] = 'Отрицательные значения'
            results.append(result)
            continue
        
        try:
            # Подгонка модели
            coeffs = model['fit_func'](x, y)
            y_pred = model['predict'](x, coeffs)
            
            # Расчет метрик
            s, sko, r2 = calculate_scores(y, y_pred)
            pearson = pearson_coeff(x, y) if model['name'] == 'Линейная' else None
            
            result.update({
                'valid': True,
                'coeffs': coeffs,
                'sko': sko,
                'r2': r2,
                'pearson': pearson,
                'y_pred': y_pred,
                'residuals': y_pred - y
            })
            
        except Exception as e:
            result['error'] = str(e)
        
        results.append(result)
    
    # Вывод результатов
    print("\nРезультаты аппроксимации:")
    for res in results:
        print(f"\n{res['name']}:")
        if not res.get('valid', False):
            print(f" Ошибка: {res.get('error', 'Не удалось подогнать')}")
            continue
        
        print(f" Коэффициенты: {np.round(res['coeffs'], 4)}")
        print(f" СКО: {res['sko']:.4f}")
        print(f" R²: {res['r2']:.4f}")
        
        if res['pearson'] is not None:
            print(f" Коэффициент Пирсона: {res['pearson']:.4f}")
        
        # Интерпретация R²
        r2_msg = "Отличная аппроксимация" if res['r2'] >= 0.9 else \
                 "Хорошая аппроксимация" if res['r2'] >= 0.7 else \
                 "Удовлетворительная" if res['r2'] >= 0.5 else "Слабая"
        print(f" {r2_msg}")
    
    # Выбор лучшей модели
    valid_models = [res for res in results if res.get('valid', False)]
    if valid_models:
        best = min(valid_models, key=lambda m: m['sko'])
        print(f"\nНаилучшая модель: {best['name']} (СКО: {best['sko']:.4f})")
    else:
        print("\nНет подходящих моделей")
        return
    
    # Построение графиков
    plot_results(x, y, valid_models, MODELS)

if __name__ == "__main__":
    main()
