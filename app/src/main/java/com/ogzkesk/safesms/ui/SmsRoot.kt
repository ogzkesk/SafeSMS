package com.ogzkesk.safesms.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.safesms.activity.SmsActivity
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SmsRoot(event: SmsActivity.Event) {

    Timber.d("SmsRoot recomposition")
    val context = LocalContext.current

    ErrorDialog(errorDialogState = event == SmsActivity.Event.PERMISSION_DECLINED) {
        val intent = Intent(android.provider.Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
        context.startActivity(intent)
    }

}