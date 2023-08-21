package com.ogzkesk.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.chat.content.DEFAULT_ARG
import com.ogzkesk.core.ui.navigation.Routes


fun NavGraphBuilder.chat(navController: NavHostController) {


    composable(
        route = Routes.Chat.route,
        arguments = Routes.Chat.arguments
    ) { entry ->

        Chat(
            id = entry.arguments?.getInt(Routes.Chat.THREAD_ID_KEY) ?: DEFAULT_ARG,
            onPopBackstack = navController::popBackStack,
            onNavigate = navController::navigate
        )
    }
}