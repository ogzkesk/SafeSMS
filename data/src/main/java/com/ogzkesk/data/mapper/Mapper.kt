package com.ogzkesk.data.mapper

import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage

fun MessageEntity.toSmsMessage() : SmsMessage {
    return SmsMessage(
        isSpam = isSpam,
        isFav = isFav,
        isRead = isRead,
        type = type,
        name = name,
        message = message,
        sender = sender,
        date = date,
        id = id
    )
}

fun SmsMessage.toMessageEntity() : MessageEntity {
    return MessageEntity(
        id = id,
        isSpam = isSpam,
        message = message,
        isFav = isFav,
        isRead = isRead,
        type = type,
        name = name,
        sender = sender,
        date = date
    )
}

fun Contact.toContactEntity() : ContactEntity {
    return ContactEntity(
        id = id,
        name = name,
        number = number
    )
}

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        name = name,
        number = number
    )
}