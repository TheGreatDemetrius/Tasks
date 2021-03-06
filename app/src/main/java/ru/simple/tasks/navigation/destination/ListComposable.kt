package ru.simple.tasks.navigation.destination

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.simple.tasks.ui.screens.list.ListScreen
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.LIST_ARGUMENT_KEY
import ru.simple.tasks.util.Constants.LIST_SCREEN

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,//параметр для определения состояния перехода к экрану конкретной задачи
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type =
                NavType.StringType//задаем строковой тип навигации, т.к. LIST_ARGUMENT_KEY является строкой
        })
    ) { navBackStackEntry ->
        //LIST_ARGUMENT_KEY переводим в формат действия
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY)
        val actionString = if (action.isNullOrEmpty()) Action.NO_ACTION else Action.valueOf(action)
        var actionState by rememberSaveable {
            mutableStateOf(Action.NO_ACTION)
        }

        //когда action меняет свое сотояние (т.е. нажимаем на одну из кнопок действия), тогда изменяем значение переменной action в классе SharedViewModel
        LaunchedEffect(key1 = actionState) {
            if (actionString != actionState) {
                actionState = actionString
                sharedViewModel.action.value = actionString
            }
        }
        val databaseAction by sharedViewModel.action
        ListScreen(
            action = databaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}