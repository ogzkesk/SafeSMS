package com.ogzkesk.safesms.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SmsService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}