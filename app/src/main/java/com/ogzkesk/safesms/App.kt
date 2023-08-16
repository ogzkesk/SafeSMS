package com.ogzkesk.safesms

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initCrashlytics()
    }

    private fun initLogging(){
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initCrashlytics(){
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(
            BuildConfig.DEBUG.not()
        )
    }
}