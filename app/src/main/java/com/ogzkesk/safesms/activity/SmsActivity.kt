package com.ogzkesk.safesms.activity

import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import com.ogzkesk.core.ui.theme.SafeSMSTheme
import com.ogzkesk.safesms.ui.SmsRoot
import com.ogzkesk.safesms.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SmsActivity : ComponentActivity() {

    private var roleManager: RoleManager? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var event by mutableStateOf(Event.DEFAULT)


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        roleManager = getSystemService()

        edgeToEdge()
        initLauncher()
        startPermission()

        setContent {
            SafeSMSTheme {
                Surface {
                    SmsRoot(event)
                }
            }
        }
    }

    private fun initLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
                if (PermissionUtils(this).checkIsRoleHeld(roleManager)) {
                    event = Event.PERMISSION_GRANTED
                    Timber.d("activityResultLauncher RoleHeld == true")
                    finish()
                } else {
                    event = Event.PERMISSION_DECLINED
                    Timber.d("activityResultLauncher RoleHeld == false")
                }
            }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startPermission() {
        PermissionUtils(this).setAppAsSms { isOnLowerApi ->
            if (isOnLowerApi) {
                Timber.d("Sending role intent lower api")
                val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
                activityResultLauncher.launch(intent)

            } else {
                Timber.d("Sending role intent")
                val intent = roleManager?.createRequestRoleIntent(RoleManager.ROLE_SMS)
                Timber.d("intent :: $intent")
                activityResultLauncher.launch(intent)
            }
        }
    }

    private fun edgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onResume() {
        if(PermissionUtils(this).checkIsRoleHeld(roleManager)){
            Timber.d("onResume() roleHeld == true")
            event = Event.PERMISSION_GRANTED
            finish()
        }
        super.onResume()
    }

    enum class Event {
        DEFAULT, PERMISSION_GRANTED, PERMISSION_DECLINED
    }
}
