package ru.simple.tasks.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.ui.theme.*
import ru.simple.tasks.util.RequestState
import ru.simple.tasks.util.SearchAppBarState

@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<SimpleTask>>,
    searchedTasks: RequestState<List<SimpleTask>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {//проверяем находимся ли мы в стостоянии поиска
        if (searchedTasks is RequestState.Success) {
            HandleListContent(
                tasks = searchedTasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
    else
    {
        if(allTasks is RequestState.Success){
            HandleListContent(
                tasks = allTasks.data,
                navigateToTaskScreen =navigateToTaskScreen
            )
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<SimpleTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty())
        EmptyContent()
    else
        DisplayTasks(
            allTasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen
        )
}


@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    allTasks: List<SimpleTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = allTasks,//список данных
            key = { task ->//фабрика стабильных и уникальных ключей, представляющих предмет
                task.id//т.е. выдаем уникальные идентификаторы для каждого элемента списка
            }
        ) { task ->
            TaskItem(
                simpleTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    simpleTask: SimpleTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(simpleTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = simpleTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE))
                    {
                        drawCircle(
                            color = simpleTask.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = simpleTask.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        simpleTask = SimpleTask(
            0,
            "Title",
            "Description",
            Priority.HIGH
        ),
        navigateToTaskScreen = {})
}