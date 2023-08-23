package com.ogzkesk.contact

sealed interface ContactEvent {

    data class Error(val message: String) : ContactEvent

    object NavigateUp : ContactEvent

}