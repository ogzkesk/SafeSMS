package com.ogzkesk.chat

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.chat(navController: NavHostController) {
    composable(Routes.Chat.route) {
        Chat(
            navController::popBackStack,
            navController::navigate
        )
    }
}