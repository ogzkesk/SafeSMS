package com.ogzkesk.safesms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import com.ogzkesk.core.sms.ClassificationHelper
import com.ogzkesk.core.sms.SmsUtils
import com.ogzkesk.core.util.Constants.SMS_RECEIVED_ACTION
import com.ogzkesk.core.util.Constants.SMS_SENT_ACTION
import com.ogzkesk.core.util.Constants.SMS_SENT_ACTION2
import com.ogzkesk.core.util.mapSenderName
import com.ogzkesk.data.local.MessageDatabase
import com.ogzkesk.data.mapper.toContact
import com.ogzkesk.data.mapper.toMessageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class SmsReceiver() : BroadcastReceiver() {

    private var job: Job? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        Timber.d("onReceive()")
        if (intent == null || context == null) return

        if (intent.action == SMS_SENT_ACTION || intent.action == SMS_SENT_ACTION2) {
            Timber.d("Sms_Sent_Action()")
            onSendSms(context)

        }

        if (intent.action == SMS_RECEIVED_ACTION) {
            onReceiveSms(context,intent)
        }
    }

    private fun onSendSms(context: Context){
        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                Timber.d("ContentObserverSend -> onChange()")
                refreshDb(context,this)
            }
        }

        context.contentResolver.registerContentObserver(
            Telephony.Sms.Sent.CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun onReceiveSms(context: Context, intent: Intent){

        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                Timber.d("ContentObserverReceive -> onChange()")
                refreshDb(context,this)
            }
        }

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        messages.forEach {
            Timber.d("Sms_Receive_Action() :: ${it.originatingAddress}")
            Timber.d("Sms_Receive_Action() :: ${it.messageBody}")
            refreshDb(context,contentObserver)
        }
    }

    private fun refreshDb(
        context: Context,
        contentObserver: ContentObserver,
    ) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {

            val db = MessageDatabase.getDatabase(context).dao()
            val contacts = db.fetchContacts().first().map { it.toContact() }
            val messages = SmsUtils.readReceivedSms(context).mapSenderName(contacts)
            val classifiedMessages = ClassificationHelper(context)
                .classifyMessages(messages)

            db.insertMessages(
                classifiedMessages.map { it.toMessageEntity() }
            )

//            context.contentResolver.unregisterContentObserver(
//                contentObserver
//            )

            Timber.d("contentObserver.unregistered()!!")
        }
    }
}