package com.ogzkesk.chat

sealed class ChatEvent {

    data class Error(val message: String) : ChatEvent()

    data class Navigate(val route: String?) : ChatEvent()

}
