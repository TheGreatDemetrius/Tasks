package ru.simple.tasks.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.simple.tasks.R
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.ui.theme.LARGE_PADDING
import ru.simple.tasks.ui.theme.PRIORITY_INDICATOR_SIZE
import ru.simple.tasks.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = when (priority) {
                Priority.HIGH -> stringResource(id = R.string.high)
                Priority.MEDIUM -> stringResource(id = R.string.medium)
                Priority.LOW -> stringResource(id = R.string.low)
                else -> stringResource(id = R.string.none)
            },
            style = Typography.subtitle2
        )
    }
}