package com.ogzkesk.safesms.util

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.SEND_SMS
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Telephony.Sms
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ogzkesk.safesms.service.SmsService
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val CHANNEL_ID = "channelId"
const val CHANNEL_NAME = "My Channel"

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


inline fun <reified T> Context.startService() {

    if (checkSmsPermission().not()) return

    if (!isServiceRunning<T>()) {

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


fun Context.checkSmsPermission(): Boolean {

    val readPermission = ContextCompat.checkSelfPermission(this, READ_SMS)
    val receivePermission = ContextCompat.checkSelfPermission(this, RECEIVE_SMS)
    val sendPermission = ContextCompat.checkSelfPermission(this, SEND_SMS)
    val grantedCode = PackageManager.PERMISSION_GRANTED

    return !(readPermission != grantedCode || receivePermission != grantedCode || sendPermission != grantedCode)
}

fun Context.shouldShowRationale(): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, READ_SMS)
}

data class SmsMessage(
    val message: String,
    val sender: String,
    val date: Long,
    val thread: Int,
    val id: Long
)

fun Long.parseDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}


internal fun readSms(context: Context): List<SmsMessage> {

    Timber.tag("readSms").d("girdi")
    val messages = mutableListOf<SmsMessage>()
    try {
        val cursor = context.contentResolver.query(
            Sms.Inbox.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use { c ->
            val indexMessage = c.getColumnIndex(Sms.BODY)
            val indexSender = c.getColumnIndex(Sms.ADDRESS)
            val indexDate = c.getColumnIndex(Sms.DATE)
            val indexThread = c.getColumnIndex(Sms.THREAD_ID)
            val idThread = c.getColumnIndex(Sms._ID)

            with(c) {
                while (moveToNext()) {
                    messages.add(
                        SmsMessage(
                            message = getString(indexMessage),
                            sender = getString(indexSender),
                            date = getLong(indexDate),
                            thread = getInt(indexThread),
                            id = getLong(idThread)
                        )
                    )
                }
            }
        }

    } catch (e: Exception) {
        e.localizedMessage?.let {
            Timber.tag("readSms").e(it)
        }
    }

    return messages
}

internal fun removeSms(context: Context, threadId: Int) {

    Timber.tag("removeSms").d("started")
    try {
        val yyy = context.contentResolver.delete(
            Sms.CONTENT_URI,
            "thread_id = ?",
            arrayOf(threadId.toString())
        )

        println("removed count: $yyy")
        Timber.tag("removeSms").d("removed")

    } catch (e: Exception) {
        e.localizedMessage?.let {
            Timber.tag("readSms").e(it)
        }
    }
}
