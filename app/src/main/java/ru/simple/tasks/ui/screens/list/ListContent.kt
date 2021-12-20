package ru.simple.tasks.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.ui.theme.*
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.ANIMATION_DELAY
import ru.simple.tasks.util.RequestState
import ru.simple.tasks.util.SearchAppBarState

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<SimpleTask>>,
    searchedTasks: RequestState<List<SimpleTask>>,
    searchAppBarState: SearchAppBarState,
    sortState: RequestState<Priority>,
    lowPriorityTasks: List<SimpleTask>,
    highPriorityTasks: List<SimpleTask>,
    onSwipeToDelete: (Action, SimpleTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (sortState is RequestState.Success)
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED ->
                if (searchedTasks is RequestState.Success)
                    HandleListContent(
                        tasks = searchedTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
            sortState.data == Priority.NONE ->
                if (allTasks is RequestState.Success)
                    HandleListContent(
                        tasks = allTasks.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
            sortState.data == Priority.LOW ->
                HandleListContent(
                    tasks = lowPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            sortState.data == Priority.HIGH ->
                HandleListContent(
                    tasks = highPriorityTasks,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
        }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<SimpleTask>,
    onSwipeToDelete: (Action, SimpleTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty())
        EmptyContent()
    else
        DisplayTasks(
            allTasks = tasks,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    allTasks: List<SimpleTask>,
    onSwipeToDelete: (Action, SimpleTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = allTasks,//список данных
            key = { task ->//фабрика стабильных и уникальных ключей, представляющих предмет
                task.id//т.е. выдаем уникальные идентификаторы для каждого элемента списка
            }
        ) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(ANIMATION_DELAY.toLong())//добавляем задержку на время анимации
                    onSwipeToDelete(
                        Action.DELETE,
                        task
                    )
                }
            }
            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )
            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = ANIMATION_DELAY
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = ANIMATION_DELAY
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        TaskItem(simpleTask = task, navigateToTaskScreen = navigateToTaskScreen)
                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {//фон задачи при удалении
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
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