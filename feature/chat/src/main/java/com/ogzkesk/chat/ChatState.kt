package com.ogzkesk.chat

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ogzkesk.domain.model.SmsMessage

@Stable
data class ChatState(
    val isLoading: Boolean = false,
    val data: List<SmsMessage> = emptyList()
)
