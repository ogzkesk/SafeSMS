package com.ogzkesk.safesms

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.ogzkesk.core.ui.theme.SafeSMSTheme
import com.ogzkesk.safesms.ui.Root
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edgeToEdge()
        checkRole()

        setContent {
            SafeSMSTheme {
                Surface {
                    Root()
                }
            }
        }
    }

    private fun edgeToEdge(){
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }

    private fun checkRole(){
        if(isSmsRoleActive()){
            Timber.d("checkRole() == true")
            TODO("start fetching data --> calculate tensorFlow")
        } else {
            Timber.d("checkRole() == false")
            val intent = Intent(this@MainActivity,SmsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isSmsRoleActive(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
            roleManager.isRoleHeld(RoleManager.ROLE_SMS)

        } else {
            packageName == Telephony.Sms.getDefaultSmsPackage(this)
        }
    }
}