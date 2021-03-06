package ru.simple.tasks.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import ru.simple.tasks.R
import ru.simple.tasks.components.PriorityDropDown
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.ui.theme.LARGE_PADDING
import ru.simple.tasks.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = LARGE_PADDING)
            .background(color = MaterialTheme.colors.background)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            value = title,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChange = { onTitleChange(it) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.title)) }
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChange = { onDescriptionChange(it) },
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = stringResource(id = R.string.description)) }
        )
    }
}