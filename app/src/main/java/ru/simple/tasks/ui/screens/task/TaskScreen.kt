package ru.simple.tasks.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.util.Action

@Composable
fun TaskScreen(
    selectedTask: SimpleTask?,
    navigateToListScreen: (Action) -> Unit
) {
    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen)
        },
        content = {}
    )
}