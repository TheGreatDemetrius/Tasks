package ru.simple.tasks.ui.screens.list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.simple.tasks.R
import ru.simple.tasks.ui.theme.fabBackgroundColor
import ru.simple.tasks.ui.viewmodels.SharedViewModel
import ru.simple.tasks.util.SearchAppBarState

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    //переменные searchAppBarState и searchTextString уведомят, если в классе SharedViewModel их значения изменятся
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextString : String by sharedViewModel.searchTexState

    Scaffold(
        topBar = { ListAppBar(
            sharedViewModel = sharedViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextString
        ) },
        content = {
            ListContent()
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
