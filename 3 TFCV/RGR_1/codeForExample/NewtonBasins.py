from manim import *

class NewtonBasins(Scene):
    def construct(self):
        x_min, x_max = -2, 2  # Диапазон значений по оси x
        y_min, y_max = -2, 2  # Диапазон значений по оси y
        max_iter = 30         # Максимальное количество итераций
        resolution = 400      # Разрешение изображения

        # Создание и добавление изображения на сцену
        image = ImageMobject(self.newton_basins_image(x_min, x_max, y_min, y_max, max_iter, resolution))
        image.set_height(config.frame_height)  # Задаем высоту изображения в соответствии с высотой кадра
        self.add(image)

    def newton_basins_image(self, x_min, x_max, y_min, y_max, max_iter, resolution):
        import numpy as np
        from PIL import Image

        # Определение корней уравнения z^3 = 1
        roots = [
            1 + 0j,
            -0.5 + 0.86602540378j,
            -0.5 - 0.86602540378j
        ]

        # Создание массива для хранения цветов пикселей
        pixels = np.zeros((resolution, resolution, 3), dtype=np.uint8)

        for i in range(resolution):
            for j in range(resolution):
                # Преобразование индексов в комплексное число
                x = x_min + (x_max - x_min) * i / (resolution - 1)
                y = y_min + (y_max - y_min) * j / (resolution - 1)
                z = complex(x, y)

                # Итеративный метод Ньютона
                for n in range(max_iter):
                    if abs(z) > 1e-10:  # Проверка, чтобы избежать деления на ноль
                        z = z - (z**3 - 1) / (3 * z**2)
                    else:
                        break  # Прекращаем итерации, если z близко к нулю

                    # Проверка, к какому корню приблизилась точка
                    for k, root in enumerate(roots):
                        if abs(z - root) < 1e-3:  # Если z близко к корню
                            color = [0, 0, 0]
                            color[k] = 255  # Устанавливаем цвет для каждого корня
                            pixels[j, i] = color
                            break
                    else:
                        continue
                    break

        # Создание изображения из массива пикселей
        return Image.fromarray(pixels)
