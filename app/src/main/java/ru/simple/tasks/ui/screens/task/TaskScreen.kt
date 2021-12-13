package ru.simple.tasks.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import ru.simple.tasks.util.Action

@Composable
fun TaskScreen(navigateToListScreen: (Action) -> Unit) {
    Scaffold(
        topBar = {
                 TaskAppBar(navigateToListScreen = navigateToListScreen)
        },
        content = {}
    )
}