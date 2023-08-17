package com.ogzkesk.core.util

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.SEND_SMS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.text.format.DateUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ogzkesk.core.R
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Duration.Companion.days


const val CHANNEL_ID = "channelId"
const val CHANNEL_NAME = "My Channel"

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

fun Long.formatDate() : String {
    val formatter =  SimpleDateFormat("EE, HH:mm", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.getElapsedTime(context:Context) : String {
    val date = Date(this)
    return date.getTimeDiffAsString(context)
}

fun Date.getTimeDiffAsString(context: Context): String {
    return if (Date().time - this.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        context.getString(R.string.just_now)
    } else {
        DateUtils.getRelativeTimeSpanString(
            this.time,
            Date().time,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE
        ).toString()
    }
}
