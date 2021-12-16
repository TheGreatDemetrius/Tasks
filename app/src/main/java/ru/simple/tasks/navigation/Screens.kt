package ru.simple.tasks.navigation

import androidx.navigation.NavHostController
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.LIST_SCREEN
import ru.simple.tasks.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }//удаляем заставку из стека экранов
        }
    }
    val list: (taskId: Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")//передаем (обязательный параметр) индентификатор в LIST_SCREEN
    }
    val task: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action.name}") {//передаем (как обязательный параметр) действие (из класса Action) в LIST_SCREEN
            popUpTo(LIST_SCREEN) { inclusive = true }//и возвращаемся на LIST_SCREEN
        }
    }
}