package ru.simple.tasks.util
//класс для контроля состояния коллекции потоков списка при получении всех задач

//sealed ограничивает возможность создания подклассов
sealed class RequestState<out T> {//модификатор out определяет вариантность обобщения

    //ключевое слово object указывает, что класс реализует Singleton
    object Idle :
        RequestState<Nothing>()//используем тип Nothing, потому что не собираемся ничего возвращать

    object Loading :
        RequestState<Nothing>()//используем тип Nothing, потому что не собираемся ничего возвращать

    //классы данных могут реализовать интерфейсы, а также наследоваться от других классов
    data class Success<T>(val data: T) :
        RequestState<T>()//наследуемся от RequestState, который возвращает все данные

    data class Error(val error: Throwable) :
        RequestState<Nothing>()//наследуемся от RequestState, который ничего не возвращает
}