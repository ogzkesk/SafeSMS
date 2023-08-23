package com.ogzkesk.safesms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.ogzkesk.core.sms.ClassificationHelper
import com.ogzkesk.core.util.Constants.SMS_RECEIVED_ACTION
import com.ogzkesk.core.util.Constants.SMS_SENT_ACTION
import com.ogzkesk.core.util.Constants.SMS_SENT_ACTION2
import com.ogzkesk.core.util.mapSenderName
import com.ogzkesk.data.local.MessageDatabase
import com.ogzkesk.data.mapper.toContact
import com.ogzkesk.data.mapper.toMessageEntity
import com.ogzkesk.domain.model.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class SmsReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob())


    override fun onReceive(context: Context?, intent: Intent?) {

        Timber.d("onReceive()")
        if (intent == null || context == null) return
        val action = intent.action

        if (action == SMS_SENT_ACTION || action == SMS_SENT_ACTION2) {
            onSendSms(context,intent)
        }

        if (action == SMS_RECEIVED_ACTION) {
            onReceiveSms(context, intent)
        }
    }

    private fun onSendSms(context: Context, intent: Intent) {
        Timber.d("onSendSms()")

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessages = messages.map { sms ->

            Timber.d("Address :: ${sms.originatingAddress}")
            Timber.d("Message :: ${sms.messageBody}")
            Timber.d("IndexOnIcc :: ${sms.indexOnIcc}")

            SmsMessage(
                isSpam = false,
                isFav = false,
                isRead = false,
                type = SmsMessage.SENT,
                message = sms.messageBody ?: sms.displayMessageBody ?: "",
                sender = sms.originatingAddress ?: sms.displayOriginatingAddress ?: "",
                date = sms.timestampMillis,
                thread = (0..9999999).random(),
                id = sms.indexOnIcc.toLong(),
                name = ""
            )
        }

//        refreshDb(context,smsMessages)

    }

    private fun onReceiveSms(context: Context, intent: Intent) {
        Timber.d("onReceiveSms()")

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessages = messages.map { sms ->

            Timber.d("Address :: ${sms.originatingAddress}")
            Timber.d("Message :: ${sms.messageBody}")
            Timber.d("IndexOnIcc :: ${sms.indexOnIcc}")

            SmsMessage(
                isSpam = false,
                isFav = false,
                isRead = false,
                type = SmsMessage.RECEIVED,
                message = sms.messageBody ?: sms.displayMessageBody ?: "",
                sender = sms.originatingAddress ?: sms.displayOriginatingAddress ?: "",
                date = sms.timestampMillis,
                thread = (0..9999999).random(),
                id = sms.indexOnIcc.toLong(),
                name = ""
            )
        }

//        refreshDb(context,smsMessages)
    }

    private fun refreshDb(context: Context,messages: List<SmsMessage>) {
        scope.launch(Dispatchers.IO) {

            val db = MessageDatabase.getDatabase(context).dao()
            val contacts = db.fetchContacts().first().map { it.toContact() }

            val classified = ClassificationHelper(context)
                .classifyMessages(
                    messages.mapSenderName(contacts)
                )

            db.insertMessages(
                classified.map { it.toMessageEntity() }
            )
        }
    }
}
