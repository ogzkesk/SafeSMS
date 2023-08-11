package com.ogzkesk.safesms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ogzkesk.safesms.service.SmsService
import com.ogzkesk.safesms.util.startService
import timber.log.Timber

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive()")
        if(intent != null){

            if(intent.action == Intent.ACTION_BOOT_COMPLETED){
                context?.startService<SmsService>()
            }
        }
    }
}