package com.ogzkesk.safesms.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.WindowCompat
import com.ogzkesk.core.ui.theme.SafeSMSTheme
import com.ogzkesk.safesms.ui.Root
import com.ogzkesk.safesms.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var roleState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate()")
        edgeToEdge()
        checkRole()

        setContent {
            SafeSMSTheme {
                Surface {
                    Root(roleState.value)
                }
            }
        }
    }

    override fun onStart() {
        Timber.d("onStart()")
        super.onStart()
    }

    override fun onResume() {
        Timber.d("onResume()")
        super.onResume()
    }

    private fun edgeToEdge(){
        WindowCompat.setDecorFitsSystemWindows(window,false)
    }

    private fun checkRole(){
        if(PermissionUtils(this).isSmsRoleActive()){
            Timber.d("checkRole() == true")
            roleState.value = true
        } else {
            Timber.d("checkRole() == false")
            val intent = Intent(this@MainActivity, SmsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}