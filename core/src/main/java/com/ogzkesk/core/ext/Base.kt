package com.ogzkesk.core.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(message: String, @StringRes resId: Int? = null){

    if(resId != null){
        Toast.makeText(this,getString(resId),Toast.LENGTH_SHORT).show()
        return
    }

    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}



