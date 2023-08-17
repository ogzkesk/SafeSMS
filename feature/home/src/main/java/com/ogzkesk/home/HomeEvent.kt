package com.ogzkesk.home

sealed interface HomeEvent {

    data class Error(val message: String) : HomeEvent

    data class Navigate(val route: String?) : HomeEvent

}