package com.ogzkesk.contact

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.contact(navController: NavHostController){
    composable(Routes.Contacts.route){
        Contact(
            navController::popBackStack,
            navController::navigate
        )
    }
}