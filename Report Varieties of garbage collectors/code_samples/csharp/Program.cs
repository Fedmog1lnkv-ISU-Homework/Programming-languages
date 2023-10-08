// Тест ручной сборки
Test1();
// Запускаем сборку мусора вручную
GC.Collect();
// Ждём завершения всех финализаторов объектов, которые были помечены для уничтожения.
GC.WaitForPendingFinalizers();
Console.WriteLine("Manual garbage collection performed\n");

// Тест using
Test2();


void Test1()
{
    List<SomeObject> objectList = new List<SomeObject>();

    for (int i = 0; i < 5; i++)
    {
        objectList.Add(new SomeObject(i + 1));
    }

}

void Test2()
{
    // Используем using для автоматического вызова Dispose()
    using (Person tom = new Person("Tom"))
    {
        // Выводим имя объекта
        Console.WriteLine($"Name: {tom.Name}");
    }

    // После завершения using объект tom уничтожается
    Console.WriteLine("Конец метода Test");
}

// Класс Person реализует интерфейс IDisposable
public class Person : IDisposable
{
    public string Name { get; }

    public Person(string name) => Name = name;

    // Реализация метода Dispose из интерфейса IDisposable
    public void Dispose() => Console.WriteLine($"{Name} has been disposed");
}

class SomeObject
{
    private int Id;

    public SomeObject(int id)
    {
        Id = id;
    }

    ~SomeObject()
    {
        Console.WriteLine($"SomeObject {Id} has been destroyed");
    }
}