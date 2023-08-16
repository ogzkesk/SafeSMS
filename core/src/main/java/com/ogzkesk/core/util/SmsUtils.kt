package com.ogzkesk.core.util

import android.content.Context
import android.provider.Telephony
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber
import java.lang.RuntimeException

object SmsUtils {

    fun readSms(context: Context): List<SmsMessage> {

        val messages = mutableListOf<SmsMessage>()
        try {
            val cursor = context.contentResolver.query(
                Telephony.Sms.Inbox.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use { c ->
                val indexMessage = c.getColumnIndex(Telephony.Sms.BODY)
                val indexSender = c.getColumnIndex(Telephony.Sms.ADDRESS)
                val indexDate = c.getColumnIndex(Telephony.Sms.DATE)
                val indexThread = c.getColumnIndex(Telephony.Sms.THREAD_ID)
                val idThread = c.getColumnIndex(Telephony.Sms._ID)

                with(c) {
                    while (moveToNext()) {
                        messages.add(
                            SmsMessage(
                                isSpam = false,
                                isFav = false,
                                message = getString(indexMessage),
                                sender = getString(indexSender),
                                date = getLong(indexDate),
                                thread = getInt(indexThread),
                                id = getLong(idThread)
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            e.localizedMessage?.let {
                Timber.tag("readSms").e(it)
            }
        }

        return messages
    }

    fun removeSms(context: Context, threadId: Int) {

        Timber.tag("removeSms").d("started")
        try {
            val yyy = context.contentResolver.delete(
                Telephony.Sms.CONTENT_URI,
                Constants.REMOVE_QUERY,
                arrayOf(threadId.toString())
            )

            Timber.tag("removeSms").d("removed count :: $yyy")

        } catch (e: Exception) {
            e.localizedMessage?.let {
                Timber.tag("readSms").e(it)
            }
        }
    }
}