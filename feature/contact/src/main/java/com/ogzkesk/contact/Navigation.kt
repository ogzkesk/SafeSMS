package com.ogzkesk.contact

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ogzkesk.core.ui.navigation.Routes

fun NavGraphBuilder.contact(onNavigateUp: () -> Unit){
    composable(Routes.Contacts.route){
        Contact(onNavigateUp = onNavigateUp)
    }
}