package ru.simple.tasks.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.simple.tasks.ui.screens.task.TaskScreen
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.TASK_ARGUMENT_KEY
import ru.simple.tasks.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit//параметр для определения состояния перехода к списку задач
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type =
                NavType.IntType//задаем целочисленный тип навигации, т.к. TASK_ARGUMENT_KEY является числом
        })
    ) { navBackStackEntry ->
        val taskId =
            navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)//передаем идетификатор (не null, т.к. id имеет свойство autoincrement)
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {//загружаем данные для задачи, которую выбрали
            if (selectedTask != null || taskId == -1)//если это новая задача или задача не восстановленная после удаления
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)
        }

        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}