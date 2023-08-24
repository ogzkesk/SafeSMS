package com.ogzkesk.core.sms

import android.content.Context
import android.provider.ContactsContract
import android.provider.Telephony
import com.ogzkesk.core.util.Constants
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber

object SmsUtils {

    fun readReceivedSms(context: Context): List<SmsMessage> {

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
                val idThread = c.getColumnIndex(Telephony.Sms._ID)

                with(c) {
                    while (moveToNext()) {
                        messages.add(
                            SmsMessage(
                                isSpam = false,
                                isFav = false,
                                isRead = false,
                                type = SmsMessage.RECEIVED,
                                name = "",
                                message = getString(indexMessage) ?: "",
                                sender = getString(indexSender) ?: "",
                                date = getLong(indexDate),
                                id = getLong(idThread)
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            e.localizedMessage?.let {
                Timber.e(it)
            }
        }

        return messages.apply { addAll(readSentSms(context)) }
    }

    private fun readSentSms(context: Context): List<SmsMessage> {

        val messages = mutableListOf<SmsMessage>()
        try {
            val cursor = context.contentResolver.query(
                Telephony.Sms.Sent.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use { c ->
                val indexMessage = c.getColumnIndex(Telephony.Sms.BODY)
                val indexSender = c.getColumnIndex(Telephony.Sms.ADDRESS)
                val indexDate = c.getColumnIndex(Telephony.Sms.DATE)
                val idThread = c.getColumnIndex(Telephony.Sms._ID)

                with(c) {
                    while (moveToNext()) {
                        messages.add(
                            SmsMessage(
                                isSpam = false,
                                isFav = false,
                                isRead = true,
                                type = SmsMessage.SENT,
                                name = "",
                                message = getString(indexMessage) ?: "",
                                sender = getString(indexSender) ?: "",
                                date = getLong(indexDate),
                                id = getLong(idThread)
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            e.localizedMessage?.let {
                Timber.e(it)
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
                Timber.e(it)
            }
        }
    }

    fun readContacts(context: Context) : List<Contact> {

        val messages = mutableListOf<Contact>()
        try {
            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use { c ->
                val indexName = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val indexTel = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val indexId = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

                with(c) {
                    while (moveToNext()) {
                        messages.add(
                            Contact(
                                id = getInt(indexId),
                                name = getString(indexName) ?: "",
                                number = getString(indexTel) ?: ""
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            e.localizedMessage?.let {
                Timber.e(it)
            }
        }

        return messages
    }
}
