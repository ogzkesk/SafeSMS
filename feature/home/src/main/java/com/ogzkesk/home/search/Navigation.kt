package com.ogzkesk.home.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.search(navController : NavHostController){
    composable(Routes.Search.route){
        Search(
            navController::popBackStack,
            navController::navigate
        )
    }
}