package ru.simple.tasks.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFFFFFFFF)

val Colors.fabBackgroundColor: Color//на основе нашей темы изменяем цвет плавающей кнопки
@Composable
get() = if(isLight) Teal200 else Purple700//т.е. если светлая тема, то вернем бирюзовый, иначе фиолетовый

val Colors.topAppBarContentColor: Color//на основе нашей темы изменяем цвет контента для TopAppBar, который находится в классе ListAppBar
@Composable
get() = if(isLight) Color.White else LightGray//т.е. если светлая тема, то вернем белый цвет, иначе светло-серый

val Colors.topAppBarBackgroundColor: Color//на основе нашей темы изменяем цвет фона для TopAppBar, который находится в классе ListAppBar
@Composable
get() = if(isLight) LightGray else DarkGray//т.е. если светлая тема, то вернем светло-серый цвет, иначе темно-серый