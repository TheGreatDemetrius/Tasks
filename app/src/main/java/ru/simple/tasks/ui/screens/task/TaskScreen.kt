package ru.simple.tasks.ui.screens.task

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Action

@ExperimentalComposeUiApi
@Composable
fun TaskScreen(
    selectedTask: SimpleTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    val message = stringResource(id = R.string.enter_title)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    when {
                        action == Action.NO_ACTION -> navigateToListScreen(action)
                        sharedViewModel.title.value.isBlank() ->//проверяем заголовок на пустоту и пробелы
                            scope.launch {//TODO оставить Toast т.к. он отображается поверх клавиатуры
                                keyboardController?.hide()
                                scaffoldState.snackbarHostState.showSnackbar(message = message)
                            }
                        else -> navigateToListScreen(action)
                    }
                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}