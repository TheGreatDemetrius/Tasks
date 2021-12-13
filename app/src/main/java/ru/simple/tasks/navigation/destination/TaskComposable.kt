package ru.simple.tasks.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.TASK_ARGUMENT_KEY
import ru.simple.tasks.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit//параметр для определения состояния перехода к списку задач
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type =
                NavType.IntType//задаем целочисленный тип навигации, т.к. TASK_ARGUMENT_KEY является числом
        })
    ) { navBackStackEntry ->
        //val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)//передаем идетификатор
    }
}