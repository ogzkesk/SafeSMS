package com.ogzkesk.core.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class Routes(val route: String) {

    object Splash : Routes("splash")

    object Home : Routes("home")

    object Search : Routes("search")

    object Settings : Routes("settings")

    object Contacts : Routes("contact")

    object Chat : Routes("chat?sender={sender}") {

        const val SENDER_KEY = "sender"
        const val DEFAULT_ARG = "-1"

        val arguments = listOf(
            navArgument(SENDER_KEY) {
                defaultValue = DEFAULT_ARG
                type = NavType.StringType
            }
        )

        fun withArgs(sender: String): String {
            return this.route.replace("{$SENDER_KEY}", sender)
        }
    }
}
