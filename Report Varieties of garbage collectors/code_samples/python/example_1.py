import time
import gc


class GarbageCollectorExample:
    def __init__(self, value):
        self.value = value

    def __del__(self):
        print(f"Object with value {self.value} is being destroyed.")


# Отлючаем автоматическую сборку при потере ссылки на объект
gc.disable()

# Создаем список объектов
objects_list = [GarbageCollectorExample(i) for i in range(1, 6)]

# Сохраняем ссылку на второй объект
object_save = objects_list[1]

# Обнуляем ссылку на список объектов
objects_list = None


# Запускаем сборщик мусора
start_time = time.time()
gc.collect()
end_time = time.time()

print(f"Время выполнения сборщика мусора - {end_time - start_time} секунд")
