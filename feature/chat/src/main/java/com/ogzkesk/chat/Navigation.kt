package com.ogzkesk.chat

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes


fun NavGraphBuilder.chat(
    onNavigateUp: () -> Unit,
    onNavigateToContacts: (entry: NavBackStackEntry) -> Unit,
) {


    composable(
        route = Routes.Chat.route,
        arguments = Routes.Chat.arguments,
        deepLinks = Routes.Chat.deepLinks
    ) { entry ->

        Chat(
            sender = entry.arguments?.getString(Routes.Chat.SENDER_KEY) ?: Routes.Chat.DEFAULT_ARG,
            onNavigateToContacts = { onNavigateToContacts(entry) },
            onNavigateUp = onNavigateUp
        )
    }
}