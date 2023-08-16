package com.ogzkesk.data.mapper

import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.domain.model.SmsMessage

fun MessageEntity.toSmsMessage() : SmsMessage {
    return SmsMessage(
        isSpam = isSpam,
        isFav = isFav,
        message = message,
        sender = sender,
        date = date,
        thread = thread,
        id = id
    )
}

fun SmsMessage.toMessageEntity() : MessageEntity {
    return MessageEntity(
        id = id,
        isSpam = isSpam,
        message = message,
        isFav = isFav,
        sender = sender,
        thread = thread,
        date = date
    )
}