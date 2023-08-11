package com.ogzkesk.safesms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import timber.log.Timber

class SmsReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent != null){

            val smsIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            smsIntent.forEach {
                // WARNING ## can be null java ..
                Timber.d("received :: ${it.messageBody}")
            }
        }
    }
}