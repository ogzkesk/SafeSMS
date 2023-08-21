package com.ogzkesk.chat.content

import android.content.Context
import android.telephony.SmsManager
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber

const val DEFAULT_ARG = -1


internal fun List<SmsMessage>.mapAsSeen() : List<SmsMessage> {
    return map { sms ->
        SmsMessage(
            isRead = true,
            type = sms.type,
            isFav = sms.isFav,
            isSpam = sms.isSpam,
            name = sms.name,
            message = sms.message,
            sender = sms.sender,
            thread = sms.thread,
            date = sms.date,
            id = sms.id
        )
    }
}

internal fun sendSms(
    context: Context,
    contacts: List<Contact>,
    message: String,
){

    Timber.d("Gelen message :: $message")
    Timber.d("contacts :: $contacts")
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

