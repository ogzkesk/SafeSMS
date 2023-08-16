package com.ogzkesk.safesms.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import com.ogzkesk.core.R
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.safesms.activity.MainActivity
import com.ogzkesk.safesms.activity.SmsActivity
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SmsRoot(event: SmsActivity.Event) {

    Timber.d("SmsRoot recomposition")
    val context = LocalContext.current
    val errorDialogState = remember { ErrorDialogState() }

    LaunchedEffect(key1 = event) {
        when (event) {

            SmsActivity.Event.PERMISSION_DECLINED -> {
                errorDialogState.showErrorDialog(
                    R.string.permission_declined
                )
            }

            SmsActivity.Event.PERMISSION_GRANTED -> {
                (context as Activity).run {

                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    )

                    startActivity(intent)
                    finish()
                }
            }

            else -> {}
        }
    }

    ErrorDialog(errorDialogState) {
        val intent = Intent(android.provider.Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
        context.startActivity(intent)
    }
}