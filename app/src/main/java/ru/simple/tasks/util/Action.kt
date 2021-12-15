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

fun String?.toAction(): Action = when {
    this == ADD -> Action.ADD
    this == UPDATE -> Action.UPDATE
    this == DELETE -> Action.DELETE
    this == DELETE_ALL -> Action.DELETE_ALL
    this == UNDO -> Action.UNDO
    else -> Action.NO_ACTION
}