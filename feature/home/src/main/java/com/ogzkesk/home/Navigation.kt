package com.ogzkesk.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.home(navController: NavHostController) {
    composable(route = Routes.Home.route){
        Home(navController::navigate)
    }
}