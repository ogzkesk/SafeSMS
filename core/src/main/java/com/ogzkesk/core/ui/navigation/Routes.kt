package com.ogzkesk.core.ui.navigation

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Home : Routes("home")
}
