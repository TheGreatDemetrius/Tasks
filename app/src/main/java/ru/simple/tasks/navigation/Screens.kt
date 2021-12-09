package ru.simple.tasks.navigation

import androidx.navigation.NavHostController
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {//передаем (как обязательный параметр) действие (из класса Action) в LIST_SCREEN
            popUpTo(LIST_SCREEN) { inclusive = true }//и возвращаемся на LIST_SCREEN
        }
    }
    val task: (taskId: Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")//передаем (обязательный параметр) индентификатор в LIST_SCREEN
    }
}