package ru.simple.tasks.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.simple.tasks.R
import ru.simple.tasks.ui.theme.LOGO_SIZE
import ru.simple.tasks.ui.theme.MEDIUM_PADDING
import ru.simple.tasks.ui.theme.noTasksColor

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(LOGO_SIZE),
            painter = painterResource(id = R.drawable.ic_logo_round),
            contentDescription = stringResource(id = R.string.no_tasks_icon),
            tint = MaterialTheme.colors.noTasksColor
        )
        Text(
            modifier = Modifier.padding(MEDIUM_PADDING),
            text = stringResource(id = R.string.there_are_no_tasks),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = MaterialTheme.colors.noTasksColor
        )
    }
}