package ru.simple.tasks.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.simple.tasks.navigation.destination.listComposable
import ru.simple.tasks.navigation.destination.splashComposable
import ru.simple.tasks.navigation.destination.taskComposable
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Constants.LIST_SCREEN
import ru.simple.tasks.util.Constants.SPLASH_SCREEN

@ExperimentalAnimationApi
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
        startDestination = if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) SPLASH_SCREEN else LIST_SCREEN//чтобы не возникало повторной заставки на android 12
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        //устанавливаем текущий navController и startDestination
        listComposable(
            navigateToTaskScreen = screen.list,//устанавливаем параметр для функции NavGraphBuilder.listComposable из класса Screens
            //т.е. открываем из экрана списка задач экран конкретной задачи
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.task,//открываем из экрана конкретной задачи список задач
            sharedViewModel = sharedViewModel
        )
    }
}