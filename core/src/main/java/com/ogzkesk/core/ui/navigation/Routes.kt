package com.ogzkesk.core.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument



sealed class Routes(val route: String) {

    object Splash : Routes("splash")

    object Home : Routes("home")

    object Search : Routes("search")

    object Settings : Routes("settings")

    object Chat : Routes("chat?threadId={threadId}") {

        const val THREAD_ID_KEY = "threadId"

        val arguments = listOf(
            navArgument(THREAD_ID_KEY){
                defaultValue = 0
                type = NavType.IntType
            }
        )

        fun withArgs(threadId: Int): String {
            return this.route.replace("{$THREAD_ID_KEY}","$threadId")
        }
    }
}
