package ru.simple.tasks.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.simple.tasks.ui.theme.topAppBarBackgroundColor
import ru.simple.tasks.ui.theme.topAppBarContentColor
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.simple.tasks.components.PriorityItem
import ru.simple.tasks.ui.theme.LARGE_PADDING
import ru.simple.tasks.ui.theme.Typography

@Composable
fun ListAppBar() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Tasks", color = MaterialTheme.colors.topAppBarContentColor) },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_tasks),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expended by remember {//remember сохраняет значение переменной без сохранения состояния
        mutableStateOf(false)//переменная expended отвечает за показ и скрытие выпадающего меню сортировки
    }
    IconButton(onClick = { expended = true }) {
        Icon(
            //для добавления всех материальных иконок используйте implementation "androidx.compose.material:material-icons-extended:$compose_version"
            //но Google не рекомедует так делать, т.к. библиотека много весит
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_tasks),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expended,
            onDismissRequest = {
                expended = false
            }) {//onDismissRequest срабатывает когда нажимаем вне области выпадающего меню
            DropdownMenuItem(onClick = {
                expended = false//скрываем пункт меню, после того как выбрали его
                onSortClicked(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expended = false//скрываем пункт меню, после того как выбрали его
                onSortClicked(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(onClick = {
                expended = false//скрываем пункт меню, после того как выбрали его
                onSortClicked(Priority.NONE)
            }) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expended by remember {//remember сохраняет значение переменной без сохранения состояния
        mutableStateOf(false)//переменная expended отвечает за показ и скрытие выпадающего меню сортировки
    }
    IconButton(onClick = { expended = true }) {
        Icon(
            //для добавления всех материальных иконок используйте implementation "androidx.compose.material:material-icons-extended:$compose_version"
            //но Google не рекомедует так делать, т.к. библиотека много весит
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expended,
            onDismissRequest = { expended = false }) {
            DropdownMenuItem(
                onClick = {
                    expended = false
                    onDeleteClicked()
                }) {
                Text(
                    modifier = Modifier.padding(start = LARGE_PADDING),
                    text = stringResource(
                        id = R.string.delete_all_action
                    ),
                    style = Typography.subtitle2
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}