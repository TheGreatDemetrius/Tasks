package ru.simple.tasks.util

import ru.simple.tasks.util.Constants.ADD
import ru.simple.tasks.util.Constants.DELETE
import ru.simple.tasks.util.Constants.DELETE_ALL
import ru.simple.tasks.util.Constants.UNDO
import ru.simple.tasks.util.Constants.UPDATE

//перечисление действий, которые мы будем сообщать макету содержащему все задачи
enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}
