package com.ogzkesk.chat

import androidx.annotation.StringRes
import com.ogzkesk.domain.model.Contact

sealed class ChatEvent {

    data class Error(
        val message: String?,
        @StringRes val resId: Int?
    ) : ChatEvent()

    object NavigateToContacts : ChatEvent()

    object NavigateUp : ChatEvent()

    data class Call(val number: String) : ChatEvent()

    data class SendSms(val contacts: List<Contact>,val message: String) : ChatEvent()

    object ContactSelected : ChatEvent()

}
