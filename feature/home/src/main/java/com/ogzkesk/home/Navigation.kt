package com.ogzkesk.home

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.home(
    onNavigateToChat: (arg: String?, entry: NavBackStackEntry) -> Unit,
    onNavigateToSettings : (entry: NavBackStackEntry) -> Unit,
    onNavigateToSearch : (entry: NavBackStackEntry) -> Unit
) {
    composable(route = Routes.Home.route) { entry ->
        Home(
            onNavigateToChat = { arg -> onNavigateToChat(arg, entry) },
            onNavigateToSettings = { onNavigateToSettings(entry) },
            onNavigateToSearch = { onNavigateToSearch(entry) },
        )
    }
}