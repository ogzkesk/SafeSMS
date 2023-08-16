package com.ogzkesk.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.settings(navController: NavHostController){
    composable(Routes.Settings.route){
        Settings(navController::navigate)
    }
}