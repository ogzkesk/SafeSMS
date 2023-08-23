package com.ogzkesk.splash

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.splash(
    onNavigateToHome: (entry: NavBackStackEntry) -> Unit
) {
    composable(route = Routes.Splash.route){ entry ->
        Splash(onNavigateToHome = { onNavigateToHome(entry) })
    }
}