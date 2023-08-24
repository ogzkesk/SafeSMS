package com.ogzkesk.core.sms

import android.content.Context
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber

class ClassificationHelper(private val context: Context) {


    fun classifyMessages(
        messages: List<SmsMessage>
    ): List<SmsMessage> {

        Timber.d("classifyMessages()")

        val client = TextClassificationClient(context).apply { load() }
        val classifiedSms = classifiedMessages(messages, client)
        client.unload()
        return classifiedSms
    }


    private fun classifiedMessages(
        messages: List<SmsMessage>,
        client: TextClassificationClient,
    ): List<SmsMessage> {

        Timber.d("mapMessages()")

        return messages.map { sms ->

            val category = client.classify(sms.message)

            if (category.isEmpty() || category.size < 2) return@map sms

            val score = category[1].score

            if (score >= 0.6f) {

                SmsMessage(
                    isSpam = true,
                    isFav = sms.isFav,
                    isRead = sms.isRead,
                    type = sms.type,
                    name = sms.name,
                    message = sms.message,
                    sender = sms.sender,
                    date = sms.date,
                    id = sms.id
                )
            } else {
                sms
            }
        }
    }
}