package com.ogzkesk.core.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import timber.log.Timber

class AppNavController(val navController: NavHostController) {


    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateUp() {
        navController.navigateUp()
    }


    fun navigateToHome(entry: NavBackStackEntry) {
        if (entry.isLifecycleResumed()) {
            navController.popBackStack()
            navController.navigate(Routes.Home.route)
        }
    }


    fun navigateToContacts(entry: NavBackStackEntry) {
        if (entry.isLifecycleResumed()) {
            navController.navigate(Routes.Contacts.route)
        }
    }

    fun navigateToSettings(entry: NavBackStackEntry) {
        if (entry.isLifecycleResumed()) {
            navController.navigate(Routes.Settings.route)
        }
    }

    fun navigateToSearch(entry: NavBackStackEntry) {
        if (entry.isLifecycleResumed()) {
            navController.navigate(Routes.Search.route)
        }
    }

    fun navigateToChat(arg: String?,entry: NavBackStackEntry) {

        if (!entry.isLifecycleResumed()) return

        if (arg == null) {
            navController.navigate(Routes.Chat.route)
            return
        }
        Timber.d("asıl route :: ${Routes.Chat.withArgs(arg)}")
        navController.navigate(Routes.Chat.withArgs(arg))
    }

}