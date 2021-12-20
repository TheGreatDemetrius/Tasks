package ru.simple.tasks.ui.screens.task

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.Action

@Composable
fun TaskScreen(
    selectedTask: SimpleTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    val context = LocalContext.current
    val msg = stringResource(id = R.string.enter_title)

    BackHandler {
        navigateToListScreen(Action.NO_ACTION)
    }

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    when {
                        action == Action.NO_ACTION -> navigateToListScreen(action)
                        sharedViewModel.title.value.isBlank() -> Toast.makeText(
                            context,
                            msg,
                            Toast.LENGTH_SHORT
                        ).show()//проверяем заголовок на пустоту и пробелы
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
                    sharedViewModel.updateDescription(it)
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}

