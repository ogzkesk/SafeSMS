package com.ogzkesk.chat.content.scaffold

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.ogzkesk.domain.model.SmsMessage

internal fun LazyListScope.messageSection(data: List<SmsMessage>){
    items(
        items = data,
        key = { it.id },
        itemContent = { message ->
            MessageContent(message)
        }
    )
}