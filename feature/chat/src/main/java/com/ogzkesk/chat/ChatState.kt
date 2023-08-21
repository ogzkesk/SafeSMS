package com.ogzkesk.chat

import androidx.compose.runtime.Stable
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage

@Stable
data class ChatState(
    val isLoading: Boolean = false,
    val data: List<SmsMessage> = emptyList(),
    val contacts : List<Contact> = emptyList(),
    val selectedContacts: List<Contact> = emptyList()
)
