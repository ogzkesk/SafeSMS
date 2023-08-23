package com.ogzkesk.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.settings(onNavigateUp: () -> Unit){
    composable(Routes.Settings.route){
        Settings(onNavigateUp = onNavigateUp)
    }
}