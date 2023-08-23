package com.ogzkesk.home.search

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.search(
    onNavigateUp : () -> Unit,
    onNavigateToChat: (arg: String?,entry: NavBackStackEntry) -> Unit
){
    composable(Routes.Search.route){ entry ->
        Search(
            onNavigateUp = onNavigateUp,
            onNavigateToChat = { arg -> onNavigateToChat(arg, entry) }
        )
    }
}