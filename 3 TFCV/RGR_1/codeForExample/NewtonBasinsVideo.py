from manim import *
import numpy as np
from PIL import Image

class NewtonBasinsZoom(Scene):
    def construct(self):
        # Задаем параметры фрактала
        x_min, x_max = -2, 2
        y_min, y_max = -2, 2
        max_iter = 50
        resolution = 500
        
        # Начальные настройки изображения
        image = ImageMobject(self.newton_basins_image(x_min, x_max, y_min, y_max, max_iter, resolution))
        image.set_height(config.frame_height)
        self.add(image)
        
        # Анимация зума
        for zoom in range(5):
            scale_factor = 0.5  # Уменьшаем масштаб для увеличения
            x_min, x_max = x_min * scale_factor, x_max * scale_factor
            y_min, y_max = y_min * scale_factor, y_max * scale_factor
            new_image = ImageMobject(self.newton_basins_image(x_min, x_max, y_min, y_max, max_iter, resolution))
            new_image.set_height(config.frame_height)
            self.play(Transform(image, new_image), run_time=2)
            self.wait(1)

    def newton_basins_image(self, x_min, x_max, y_min, y_max, max_iter, resolution):
        # Создаем пустое изображение
        img = Image.new("RGB", (resolution, resolution))
        pixels = img.load()

        # Задаем более светлые цвета для каждого аттрактора
        attractor_colors = [
            (173, 216, 230),  # Светло-голубой
            (255, 182, 193),  # Светло-розовый
            (144, 238, 144),  # Светло-зеленый
        ]

        # Функция и её производная
        def f(z):
            return z**3 - 1

        def f_prime(z):
            return 3 * z**2

        # Вычисляем фрактал методом Ньютона
        for x in range(resolution):
            for y in range(resolution):
                # Преобразуем координаты пикселей в комплексную плоскость
                zx = x_min + (x / resolution) * (x_max - x_min)
                zy = y_min + (y / resolution) * (y_max - y_min)
                z = complex(zx, zy)

                for i in range(max_iter):
                    try:
                        # Итерация метода Ньютона
                        z -= f(z) / f_prime(z)
                    except ZeroDivisionError:
                        break

                # Определяем, к какому аттрактору сходится точка
                distances = [abs(z - attractor) for attractor in [1, complex(-0.5, np.sqrt(3) / 2), complex(-0.5, -np.sqrt(3) / 2)]]
                min_distance = min(distances)
                attractor_index = distances.index(min_distance)
                
                # Назначаем цвет в зависимости от аттрактора
                color = attractor_colors[attractor_index]
                brightness = int(255 * (1 - i / (max_iter * 0.7)))  # Увеличиваем яркость
                pixels[x, y] = tuple(min(255, int(c + brightness * 0.5)) for c in color)

        return img
