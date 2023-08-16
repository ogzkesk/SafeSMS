package com.ogzkesk.safesms.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import com.ogzkesk.core.util.checkSmsPermission
import com.ogzkesk.safesms.service.SmsService

inline fun <reified T> Context.startService() {

    if (checkSmsPermission().not()) return

    if (isServiceRunning<T>().not()) {

        val intent = Intent(this, T::class.java)
        intent.action = SmsService.Action.START.toString()

        startForegroundService(intent)
    }
}


@Suppress("DEPRECATION")
inline fun <reified T> Context.isServiceRunning(): Boolean {

    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    return activityManager.getRunningServices(Integer.MAX_VALUE).any {
        it.service.className == T::class.java.name
    }
}