package com.ogzkesk.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

fun NavBackStackEntry.isLifecycleResumed(): Boolean {
    return lifecycle.currentState == Lifecycle.State.RESUMED
}

@Composable
fun rememberAppNavController(
    navController: NavHostController = rememberNavController()
): AppNavController {
    return remember(navController) {
        AppNavController(navController)
    }
}