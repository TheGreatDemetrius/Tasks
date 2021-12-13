package ru.simple.tasks.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.simple.tasks.navigation.destination.listComposable
import ru.simple.tasks.navigation.destination.taskComposable
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val screen = remember(navController) {
        Screens(navController = navController)//запоминаем порядок экранов
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {//устанавливаем текущий navController и startDestination
        listComposable(
            navigateToTaskScreen = screen.task,//устанавливаем параметр для функции NavGraphBuilder.listComposable из класса Screens
            //т.е. открываем из экрана списка задач экран конкретной задачи
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list//открываем из экрана конкретной задачи список задач
        )
    }
}