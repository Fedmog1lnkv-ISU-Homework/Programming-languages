import time
import gc


class GarbageCollectorExample:
    def __init__(self, value):
        self.value = value

    def __del__(self):
        print(f"Object with value {self.value} is being destroyed.")


# Включаем автоматическую сборку при потере ссылки на объект
gc.enable()

# Создаем список объектов
objects_list = [GarbageCollectorExample(i) for i in range(1, 6)]

# Обнуляем ссылку на первый объект в списке
objects_list[0] = None

print(f"Количество объектов в поколениях до сборки мусора: {len(gc.get_objects())}")

# Запускаем сборщик мусора с поколениями
gc.collect(0)  # сборка мусора для первого поколения
gc.collect(1)  # сборка мусора для второго поколения

# Выводим количество объектов в каждом поколении
print(f"Количество объектов в поколениях после сборки мусора: {len(gc.get_objects())}")
