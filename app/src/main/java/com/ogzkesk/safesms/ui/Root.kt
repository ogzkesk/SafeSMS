package com.ogzkesk.safesms.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.chat.chat
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.home.home
import com.ogzkesk.home.search.search
import com.ogzkesk.settings.settings
import com.ogzkesk.splash.splash
import timber.log.Timber

@Composable
fun Root(roleState: Boolean) {

    val navController = rememberNavController()
    if(!roleState) return



    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        splash(navController)
        home(navController)
        search(navController)
        chat(navController)
        settings(navController)
    }
}