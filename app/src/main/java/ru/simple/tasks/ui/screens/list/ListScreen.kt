package ru.simple.tasks.ui.screens.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import ru.simple.tasks.R
import ru.simple.tasks.ui.theme.fabBackgroundColor
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.SearchAppBarState

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    action: Action,
    sharedViewModel: SharedViewModel,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action = action)
    }

    //переменные уведомят, если в классе SharedViewModel их значения изменятся
    //collectAsState() будет наблюдать за потоком из составной функции
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextString: String by sharedViewModel.searchTexState

    val scaffoldState = rememberScaffoldState()

    ShowSnackbar(
        scaffoldState = scaffoldState,
        taskTitle = sharedViewModel.title.value,
        action = action,
        onComplete = {
            sharedViewModel.action.value = it
        },//вызываем функцию, которая будет выполнять действия
        onUndoClicked = {
            sharedViewModel.action.value = it
        }//проверяем нажимаем ли мы отмену задачи
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextString
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateTaskFields(selectedTask = task)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(onClick = {
        onFabClicked(-1)//предаем идентификатор задачи -1, т.к. при создании задачи идентификатор не нужен
    }, backgroundColor = MaterialTheme.colors.fabBackgroundColor) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun ShowSnackbar(
    scaffoldState: ScaffoldState,
    taskTitle: String,
    action: Action,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit
) {
    val scope = rememberCoroutineScope()
    val deleteAllMessage = stringResource(id = R.string.delete_all)
    val deleteLabel = stringResource(id = R.string.delete)
    val undoLabel = stringResource(id = R.string.undo)
    val okLabel = stringResource(id = R.string.ok)
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {//вызываем Snackbar после нажатия любого действия, кроме NO_ACTION
            scope.launch {
                val snackbarResult =//snackbarResult проверяет нажали ли на actionLabel
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = if (action == Action.DELETE_ALL) deleteAllMessage else "${action.name}: $taskTitle",
                        actionLabel = if (action.name == deleteLabel) undoLabel else okLabel
                    )
                undoDeletedTask(
                    action = action,
                    snackbarResult = snackbarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun undoDeletedTask(
    action: Action,
    snackbarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed && action == Action.DELETE)
        onUndoClicked(Action.UNDO)
}