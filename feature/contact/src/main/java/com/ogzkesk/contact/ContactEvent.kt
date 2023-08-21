package com.ogzkesk.contact

sealed interface ContactEvent {

    data class Error(val message: String) : ContactEvent

    data class Navigate(val route: String?) : ContactEvent

}