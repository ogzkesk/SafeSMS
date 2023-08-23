package com.ogzkesk.home

import androidx.navigation.NavBackStackEntry

sealed interface HomeEvent {

    data class Error(val message: String) : HomeEvent

    data class NavigateToChat(val arg: String?) : HomeEvent

    object NavigateToSearch: HomeEvent

    object NavigateToSettings: HomeEvent

    object NavigateUp : HomeEvent

}