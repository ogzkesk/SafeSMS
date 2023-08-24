package com.ogzkesk.safesms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ogzkesk.core.util.Constants.CHANNEL_ID
import com.ogzkesk.core.util.Constants.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initCrashlytics()
        createNotificationChannel()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(
            BuildConfig.DEBUG.not()
        )
    }

    private fun createNotificationChannel() {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        val notificationManager = getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(channel)
    }
}