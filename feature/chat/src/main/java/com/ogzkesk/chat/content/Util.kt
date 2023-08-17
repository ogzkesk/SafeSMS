package com.ogzkesk.chat.content

import com.ogzkesk.domain.model.SmsMessage

internal fun List<SmsMessage>.mapAsSeen() : List<SmsMessage> {
    return map { sms ->
        SmsMessage(
            isRead = true,
            isFav = sms.isFav,
            isSpam = sms.isSpam,
            message = sms.message,
            sender = sms.sender,
            thread = sms.thread,
            date = sms.date,
            id = sms.id
        )
    }
}