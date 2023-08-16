package com.ogzkesk.core.ui.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Search : Routes("search")
    object Chat : Routes("chat")
    object Settings : Routes("settings")
}
