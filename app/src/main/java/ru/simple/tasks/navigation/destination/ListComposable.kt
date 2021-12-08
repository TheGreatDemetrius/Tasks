package ru.simple.tasks.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.simple.tasks.util.Constants.LIST_ARGUMENT_KEY
import ru.simple.tasks.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit//параметр для определения состояния перехода к экрану конкретной задачи
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type =
                NavType.StringType//задаем строковой тип навигации, т.к. LIST_ARGUMENT_KEY является строкой
        })
    ) {}
}