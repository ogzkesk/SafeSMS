package com.ogzkesk.chat.content

import android.content.Context
import android.telephony.SmsManager
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage


fun List<SmsMessage>.mapAsSeen() : List<SmsMessage> {
    return map { sms ->
        SmsMessage(
            isRead = true,
            type = sms.type,
            isFav = sms.isFav,
            isSpam = sms.isSpam,
            name = sms.name,
            message = sms.message,
            sender = sms.sender,
            date = sms.date,
            id = sms.id
        )
    }
}

fun sendSms(
    context: Context,
    contacts: List<Contact>,
    message: String,
){

    context.getSystemService(SmsManager::class.java).apply {
        contacts.forEach {
            sendTextMessage(
                it.number,
                null,
                message,
                null,
                null
            )
        }
    }
}

