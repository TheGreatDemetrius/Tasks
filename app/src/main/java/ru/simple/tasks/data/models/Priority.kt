package ru.simple.tasks.data.models

import androidx.compose.ui.graphics.Color
import ru.simple.tasks.ui.theme.HighPriorityColor
import ru.simple.tasks.ui.theme.LowPriorityColor
import ru.simple.tasks.ui.theme.MediumPriorityColor
import ru.simple.tasks.ui.theme.NonePriorityColor

enum class Priority(val color : Color) {
    LOW(LowPriorityColor),
    MEDIUM(MediumPriorityColor),
    HIGH(HighPriorityColor),
    NONE(NonePriorityColor)
}