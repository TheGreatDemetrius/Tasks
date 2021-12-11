package ru.simple.tasks.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import ru.simple.tasks.components.PriorityItem
import ru.simple.tasks.ui.theme.*

@Composable
fun ListAppBar() {
    SearchAppBar(
        text = "Search",
        onTextChange = {},
        onClosedClicked = {},
        onSearchClicked = {}
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
        DropdownMenu(//TODO оставить только иконку (без выпадающего списка)
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

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClosedClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,//стандартная тень для TopAppBar
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)//возвращаем строку соответствующую тексту введенному в данное текстовое поле
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),//установили среднюю контрастность
                    text = "Search",//TODO в строковые ресурсы
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.disabled),//отключили контрастность кнопки, чтобы сделать иконку полупрозрачной
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",//TODO в строковые ресурсы
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onClosedClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search//установили на калавиатуру стандартную иконку поиска
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,//устанавливаем цвет курсора
                //убираем все дополнительные цвета
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
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

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onClosedClicked = {},
        onSearchClicked = {}
    )
}