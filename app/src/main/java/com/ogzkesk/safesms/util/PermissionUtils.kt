package com.ogzkesk.safesms.util

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import timber.log.Timber

class PermissionUtils(private val context:Context) {


    fun setAppAsSms(isOnLowerApi: (Boolean) -> Unit) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            askSmsRole(isOnLowerApi)
            return
        }

        askSmsRoleLowApi(isOnLowerApi)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun askSmsRole(onLowerApi: (Boolean) -> Unit) {

        val roleManager = context.getSystemService(Context.ROLE_SERVICE) as RoleManager

        if (!roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
            Timber.d("Sms Role is not available")
            return
        }

        if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
            Timber.d("App's role held as sms.")
            return
        }

        onLowerApi.invoke(false)
    }


    private fun askSmsRoleLowApi(onLowerApi: (Boolean) -> Unit) {
        if (Telephony.Sms.getDefaultSmsPackage(context) == context.packageName) {
            Timber.d("App is already default sms app")
            return
        }

        onLowerApi.invoke(true)
    }

    fun checkIsRoleHeld(roleManager: RoleManager?): Boolean {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return roleManager?.isRoleHeld(RoleManager.ROLE_SMS) ?: false
        }

        return Telephony.Sms.getDefaultSmsPackage(context) == context.packageName
    }

    fun isSmsRoleActive(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            val roleManager = context.getSystemService(Context.ROLE_SERVICE) as RoleManager
            roleManager.isRoleHeld(RoleManager.ROLE_SMS)

        } else {
            context.packageName == Telephony.Sms.getDefaultSmsPackage(context)
        }
    }
}