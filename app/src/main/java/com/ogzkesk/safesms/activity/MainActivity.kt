package com.ogzkesk.safesms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import com.ogzkesk.core.ui.theme.SafeSMSTheme
import com.ogzkesk.core.util.Constants.EXTRA_SENDER
import com.ogzkesk.core.util.checkNotificationPermission
import com.ogzkesk.safesms.MainViewModel
import com.ogzkesk.safesms.ui.Root
import com.ogzkesk.safesms.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val roleState = mutableStateOf(false)
    private val senderExtra = mutableStateOf<String?>(null)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edgeToEdge()
        checkRole()
        askNotificationPermission()
        setIntent()


        setContent {
            SafeSMSTheme {
                Surface {
                    Root(roleState.value, senderExtra.value)
                }
            }
        }
    }


    private fun edgeToEdge(){
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }

    private fun setIntent(){
        senderExtra.value = intent.getStringExtra(EXTRA_SENDER)
    }

    private fun checkRole(){
        if(PermissionUtils(this).isSmsRoleActive()){
            Timber.d("checkRole() == true")
            roleState.value = true
            getPhoneNumber()
        } else {
            Timber.d("checkRole() == false")
            val intent = Intent(this@MainActivity, SmsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPhoneNumber(){
        getSystemService<SubscriptionManager>()?.let { sm ->

            var phoneNumber = ""

            sm.activeSubscriptionInfoList.forEach { info ->
                phoneNumber = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    sm.getPhoneNumber(info.subscriptionId)
                } else {
                    info.number ?: ""
                }
            }

            viewModel.setPhoneNumber(phoneNumber)
        }
    }

    private fun askNotificationPermission(){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) return

        if(!checkNotificationPermission(this)){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1002
            )
        }
    }
}