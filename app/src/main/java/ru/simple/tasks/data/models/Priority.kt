package ru.simple.tasks.data.models

import androidx.compose.ui.graphics.Color
import ru.simple.tasks.ui.theme.HighPriorityColor
import ru.simple.tasks.ui.theme.LowPriorityColor
import ru.simple.tasks.ui.theme.MediumPriorityColor
import ru.simple.tasks.ui.theme.NonePriorityColor

enum class Priority(val color : Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}