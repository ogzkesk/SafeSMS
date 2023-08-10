package com.ogzkesk.safesms

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.ogzkesk.core.ui.theme.SafeSMSTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SmsActivity : ComponentActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TODO("intent göndermiyor")

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            println("launcher pop")
            val data = it.data
            data?.let {
                println("data let pop ${it.data}")
                println("data let pop ${it.extras}")
                println("data let pop ${it.action}")
            }
        }

        edgeToEdge()
        setAppAsSms()

        setContent {
            SafeSMSTheme {

            }
        }
    }

    private fun edgeToEdge(){
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }

    private fun setAppAsSms() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            askSmsRole()
            return
        }

        askSmsRoleLowApi()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun askSmsRole() {

        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager

        if (!roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
            Timber.d("Sms Role is not available")
            return
        }

        if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
            Timber.d("App's role held as sms.")
            return
        }

        Timber.d("Sending role intent")
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
        activityResultLauncher.launch(intent)
    }


    private fun askSmsRoleLowApi() {
        if (Telephony.Sms.getDefaultSmsPackage(this) == packageName) {
            Timber.d("App is already default sms app")
            return
        }


        Timber.d("Sending role intent low api")
        val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
        activityResultLauncher.launch(intent)

    }
}
