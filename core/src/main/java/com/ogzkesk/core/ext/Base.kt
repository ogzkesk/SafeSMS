package com.ogzkesk.core.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun Context.showToast(message: String, @StringRes resId: Int? = null) {

    if (resId != null) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
        return
    }

    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun MutableState<String>.reset() {
    if(this.value.isNotEmpty()) this.value = ""
}

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    this.launch(
        context = Dispatchers.IO,
        block = block
    )
}

fun <T> Flow<T>.flowOnIO(): Flow<T>{
    return this.flowOn(Dispatchers.IO)
}

suspend fun <T> withIOContext(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(context,block)
}