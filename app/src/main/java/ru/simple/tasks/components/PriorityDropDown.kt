package ru.simple.tasks.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.simple.tasks.data.models.Priority
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.simple.tasks.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import ru.simple.tasks.ui.theme.PRIORITY_INDICATOR_SIZE
import ru.simple.tasks.R

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expended by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue = if(expended) 180f else 0f
    )

    Row(modifier = Modifier
        .background(color = MaterialTheme.colors.background)
        .height(PRIORITY_DROP_DOWN_HEIGHT)
        .clickable {
            expended = true
        }
        .border(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
            shape = MaterialTheme.shapes.small
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        )
        {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.weight(8f),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(angle)
                .weight(1.5f),
            onClick = { expended = true }) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.drop_down_arrow_icon)
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(fraction = 0.94f),
            expanded = expended,
            onDismissRequest = { expended = false }
        ) {
            DropdownMenuItem(onClick =//первый пункт выпадающего меню
            {
                expended = false
                onPrioritySelected(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick =//второй пункт выпадающего меню
            {
                expended = false
                onPrioritySelected(Priority.MEDIUM)
            }) {
                PriorityItem(priority = Priority.MEDIUM)
            }
            DropdownMenuItem(onClick =//третий пункт выпадающего меню
            {
                expended = false
                onPrioritySelected(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }
    }
}

@Preview
@Composable
fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.LOW, onPrioritySelected = {})
}