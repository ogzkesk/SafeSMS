package com.ogzkesk.safesms.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.PendingIntentCompat
import com.ogzkesk.core.util.CHANNEL_ID
import com.ogzkesk.safesms.R
import com.ogzkesk.safesms.activity.MainActivity
import timber.log.Timber

class SmsService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand()")
        when (intent?.action) {
            Action.START.toString() -> start()
            Action.STOP.toString() -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Timber.d("Service started")

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntentCompat.getActivity(
            this,
            101,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT,
            false
        )


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("")
            .setContentText("")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        fetchMessages()

//        object : NotificationListenerService() {
//            override fun onNotificationPosted(sbn: StatusBarNotification?) {
//                Log.d(TAG, "onNotificationPosted")
//                sbn?.key ?: return
//                snoozeNotification(sbn.key, Long.MAX_VALUE)
//                Log.d(TAG, "onNotificationPosted snoozed!!")
//                super.onNotificationPosted(sbn)
//            }
//        }
    }

    private fun fetchMessages() {
//
//        removeSms(this, 339)
//        val messages = readSms(this)
//        // if(detected as a spam) --- >
//        // add to DB and removeSms(this,smsID)
//        messages.forEach {
//            Timber.d(it.toString())
//        }
    }

    private fun stop() {
        Timber.d("Service stopped")
        stopSelf()
    }


    override fun onCreate() {
        Timber.d("Service onCreate()")
        super.onCreate()
    }

    override fun onDestroy() {
        Timber.d("Service onDestroy()")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    enum class Action{
        START, STOP
    }
}