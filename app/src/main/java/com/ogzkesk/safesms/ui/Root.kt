package com.ogzkesk.safesms.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.home.home
import com.ogzkesk.splash.splash

@Composable
fun Root() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        splash(navController)
        home(navController)
    }
}