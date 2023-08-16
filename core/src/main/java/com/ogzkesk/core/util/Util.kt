package com.ogzkesk.core.util

import android.Manifest.permission.READ_SMS
import android.Manifest.permission.RECEIVE_SMS
import android.Manifest.permission.SEND_SMS
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Telephony.Sms
import android.text.format.DateUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ogzkesk.core.R
import com.ogzkesk.core.util.Constants.REMOVE_QUERY
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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

fun Long.convertHistory(context:Context) : String {
    val date = Date(this)
    return date.getTimeDiffAsString(context)
}

fun Date.getTimeDiffAsString(context: Context): String {
    return if (Date().time - this.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        context.getString(R.string.just_now)
    } else {
        DateUtils.getRelativeTimeSpanString(
            this.time, Date().time, DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE
        ).toString()
    }
}
