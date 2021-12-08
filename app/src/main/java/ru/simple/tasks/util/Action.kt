package ru.simple.tasks.util

//перечисление действий, которые мы будем сообщать макету содержащему все задачи
enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}