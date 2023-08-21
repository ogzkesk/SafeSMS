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
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val CHANNEL_ID = "channelId"

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


fun List<SmsMessage>.mapSenderName(contacts: List<Contact>) : List<SmsMessage> {

    var cIndex = -1
    var sIndex = -1
    contacts.forEachIndexed { contactIndex, c ->
        this.forEachIndexed { smsIndex, smsMessage ->
            if(smsMessage.sender == c.number){
                sIndex = smsIndex
                cIndex = contactIndex
            }
        }
    }

    return mapIndexed { index, sms ->
        if(index == sIndex){

            Timber.d("mapContactName index != -1")

            SmsMessage(
                name = contacts[cIndex].name,
                sender = sms.sender,
                isSpam = sms.isSpam,
                isFav = sms.isFav,
                isRead = sms.isRead,
                type = sms.type,
                message = sms.message,
                date = sms.date,
                thread = sms.thread,
                id = sms.id
            )
        } else {
            Timber.d("mapContactName else")
            sms
        }
    }
}