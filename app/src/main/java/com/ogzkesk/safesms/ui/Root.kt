package com.ogzkesk.safesms.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.ogzkesk.chat.chat
import com.ogzkesk.contact.contact
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.core.ui.navigation.rememberAppNavController
import com.ogzkesk.home.home
import com.ogzkesk.home.search.search
import com.ogzkesk.settings.settings
import com.ogzkesk.splash.splash

@Composable
fun Root(roleState: Boolean, sender: String?) {

    val appNavController = rememberAppNavController()
    if(!roleState) return


    NavHost(
        navController = appNavController.navController,
        startDestination = Routes.Home.route
    ) {
        home(
            appNavController::navigateToChat,
            appNavController::navigateToSettings,
            appNavController::navigateToSearch
        )
        search(
            appNavController::navigateUp,
            appNavController::navigateToChat
        )
        chat(
            appNavController::navigateUp,
            appNavController::navigateToContacts,
        )
        contact(
            appNavController::navigateUp
        )
        settings(
            appNavController::navigateUp
        )
    }

    if(sender != null) {
        appNavController.navController
            .navigate(Routes.Chat.withArgs(sender))
    }
}