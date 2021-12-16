package ru.simple.tasks.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.simple.tasks.ui.screens.splash.SplashScreen
import ru.simple.tasks.util.Constants.SPLASH_SCREEN

fun NavGraphBuilder.splashComposable(navigateToListScreen: () -> Unit) {
    composable(route = SPLASH_SCREEN) { SplashScreen(navigateToListScreen = navigateToListScreen) }
}