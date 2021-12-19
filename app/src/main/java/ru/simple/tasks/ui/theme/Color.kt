package ru.simple.tasks.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue200 = Color(0xFF80B4FF)
val Blue500 = Color(0xFF609BEB)
val Blue700 = Color(0xFF4285F4)
val Teal200 = Color(0xFF2FF3D8)

val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF34A853)
val MediumPriorityColor = Color(0xFFFBBC05)
val HighPriorityColor = Color(0xFFEA4335)
val NonePriorityColor = Color(0xFFDDDDDD)

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else NonePriorityColor

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Blue700

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else NonePriorityColor

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Blue500 else DarkGray

val Colors.noTasksColor: Color
    @Composable
    get() = if (isLight) Color.LightGray else Color.DarkGray