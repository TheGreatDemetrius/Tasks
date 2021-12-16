package ru.simple.tasks.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue200 = Color(0xFF95BAFA)
val Blue500 = Color(0xFF71A4FA)
val Blue700 = Color(0xFF4285F4)
val Teal200 = Color(0xFF2FF3D8)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFFDDDDDD)

val Colors.splashScreenBackgroundColor: Color
    @Composable
    get() = if (isLight) Blue700 else DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.fabBackgroundColor: Color
    //на основе нашей темы изменяем цвет плавающей кнопки
    @Composable
    get() = if (isLight) Teal200 else Blue700//т.е. если светлая тема, то вернем бирюзовый, иначе фиолетовый

val Colors.topAppBarContentColor: Color
    //на основе нашей темы изменяем цвет контента для TopAppBar, который находится в классе ListAppBar
    @Composable
    get() = if (isLight) Color.White else LightGray//т.е. если светлая тема, то вернем белый цвет, иначе светло-серый

val Colors.topAppBarBackgroundColor: Color
    //на основе нашей темы изменяем цвет фона для TopAppBar, который находится в классе ListAppBar
    @Composable
    get() = if (isLight) Blue500 else DarkGray//т.е. если светлая тема, то вернем фиолетовый цвет, иначе темно-серый