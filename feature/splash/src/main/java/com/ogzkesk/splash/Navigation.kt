package com.ogzkesk.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.splash(navController: NavHostController) {
    composable(route = Routes.Splash.route){
        Splash(navController::navigate)
    }
}