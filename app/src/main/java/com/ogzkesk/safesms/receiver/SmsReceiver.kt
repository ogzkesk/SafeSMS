package com.ogzkesk.safesms.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ogzkesk.core.ext.launchIO
import com.ogzkesk.core.ext.withIOContext
import com.ogzkesk.core.sms.ClassificationHelper
import com.ogzkesk.core.util.Constants.ACTION_MESSAGE
import com.ogzkesk.core.util.Constants.CHANNEL_ID
import com.ogzkesk.core.util.Constants.EXTRA_SENDER
import com.ogzkesk.core.util.Constants.NOTIFICATION_REQUEST_CODE
import com.ogzkesk.core.util.Constants.SMS_RECEIVED
import com.ogzkesk.core.util.checkNotificationPermission
import com.ogzkesk.core.util.mapSenderName
import com.ogzkesk.data.local.MessageDao
import com.ogzkesk.data.local.MessageDatabase
import com.ogzkesk.data.mapper.toContact
import com.ogzkesk.data.mapper.toMessageEntity
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.safesms.R
import com.ogzkesk.safesms.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.UUID

class SmsReceiver : BroadcastReceiver() {

    private val scope = CoroutineScope(SupervisorJob())


    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null) return

        if (intent.action == SMS_RECEIVED) {
            onReceiveSms(context, intent)
        }
    }


    private fun onReceiveSms(context: Context, intent: Intent) {
        Timber.d("onReceiveSms()")

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessages = messages.map { sms ->

            Timber.d("Address :: ${sms.originatingAddress}")
            Timber.d("Message :: ${sms.messageBody}")

            SmsMessage(
                isSpam = false,
                isFav = false,
                isRead = false,
                name = "",
                type = SmsMessage.RECEIVED,
                message = sms.messageBody ?: sms.displayMessageBody ?: "",
                sender = sms.originatingAddress ?: sms.displayOriginatingAddress ?: "",
                date = sms.timestampMillis,
                id = UUID.randomUUID().mostSignificantBits
            )
        }

        refreshDb(context,smsMessages)
        postNotification(context,smsMessages)
    }

    private fun refreshDb(context: Context,messages: List<SmsMessage>) {
        scope.launchIO {

            val db = getDatabase(context)
            val mappedMessages = mapSenderNames(context,messages)

            val classified = ClassificationHelper(context)
                .classifyMessages(mappedMessages)

            db.insertMessages(
                classified.map { it.toMessageEntity() }
            )
        }
    }

    private fun postNotification(
        context: Context,
        smsMessage: List<SmsMessage>
    ) = scope.launchIO {

        if(smsMessage.isEmpty()) return@launchIO
        if(!checkNotificationPermission(context)) return@launchIO

        val mappedMessages = mapSenderNames(context,smsMessage)

        Timber.d("postingMessage :: $mappedMessages")

        smsMessage.forEach { sms ->

            val intent = Intent(context, MainActivity::class.java).apply {
                action = ACTION_MESSAGE
                putExtra(EXTRA_SENDER,sms.sender)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(sms.name.ifEmpty { sms.sender })
                .setContentText(sms.message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            NotificationManagerCompat.from(context).notify(0,builder.build())

        }
    }


    private suspend fun mapSenderNames(
        context: Context,
        messages: List<SmsMessage>
    ) : List<SmsMessage> {

        val db = getDatabase(context)
        val mappedList = mutableListOf<SmsMessage>()

        return withIOContext {
            val contacts = db.fetchContacts().first().map { it.toContact() }
            mappedList.addAll(messages.mapSenderName(contacts))

            mappedList
        }
    }


    private fun getDatabase(context: Context) : MessageDao {
        return MessageDatabase.getDatabase(context).dao()
    }
}
