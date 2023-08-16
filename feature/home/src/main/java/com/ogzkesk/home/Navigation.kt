package com.ogzkesk.home

import android.app.Activity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.home(navController: NavHostController) {
    composable(route = Routes.Home.route){
        Home(navController::navigate)
    }
}