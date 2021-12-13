package ru.simple.tasks.ui.screens.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Delete
import ru.simple.tasks.ui.theme.topAppBarContentColor
import ru.simple.tasks.util.Action
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.ui.theme.topAppBarBackgroundColor

@Composable
fun TaskAppBar(
    selectedTask: SimpleTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedTask == null)//выбираем какой AppBar показывать
        TaskItemAppBar(navigateToListScreen = navigateToListScreen)
    else
        ExistingTaskAppBar(selectedTask = selectedTask, navigateToListScreen = navigateToListScreen)
}

@Composable
fun TaskItemAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_task),
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )
}

@Composable
fun BackAction(//нажали вернуться назад
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onBackClicked(Action.NO_ACTION)
    }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun ExistingTaskAppBar(//открыли существующую задачу
    selectedTask: SimpleTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onClosedClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            DeleteAction(onDeleteClicked = navigateToListScreen)
            UpdateAction(onUpdateClicked = navigateToListScreen)
        }
    )
}


@Composable
fun CloseAction(//нажали закрыть задачу
    onClosedClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onClosedClicked(Action.NO_ACTION)
    }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}


@Composable
fun DeleteAction(//нажали акрыть задачу
    onDeleteClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onDeleteClicked(Action.DELETE)
    }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(//нажали акрыть задачу
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onUpdateClicked(Action.UPDATE)
    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}


@Composable
fun AddAction(//нажали добавить задачу
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onAddClicked(Action.ADD)
    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_task),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Preview
@Composable
fun TaskItemAppBarPreview() {
    TaskItemAppBar(navigateToListScreen = {})
}

@Preview
@Composable
fun ExistingTaskAppBarPreview() {
    ExistingTaskAppBar(
        selectedTask = SimpleTask(
            0,
            "Title",
            "Description",
            Priority.LOW
        ),
        navigateToListScreen = {})
}