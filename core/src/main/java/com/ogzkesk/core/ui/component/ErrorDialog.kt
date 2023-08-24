package com.ogzkesk.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ogzkesk.core.R


@Stable
data class ErrorDialogState(
    @StringRes
    internal var messageResId: Int = 0,
    internal var isVisible: Boolean = false,
    internal var message: String = ""
) {
    fun showErrorDialog(message: String) {
        this.isVisible = true
        this.message = message
    }

    fun showErrorDialog(messageResId: Int) {
        this.isVisible = true
        this.messageResId = messageResId
    }
}


@Composable
fun ErrorDialog(
    state: ErrorDialogState,
    onDismiss: (() -> Unit)? = null
) {

    val isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(state.isVisible){
        if(state.isVisible){
            isVisible.value = true
        }
    }

    if (isVisible.value) {

        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message.ifEmpty { stringResource(id = state.messageResId) },
                        style = MaterialTheme.typography.titleMedium
                            .copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            vertical = 24.dp,
                            horizontal = 16.dp
                        )
                    )
                    Divider(color = DividerDefaults.color.copy(alpha = 0.5f))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                isVisible.value = false
                                onDismiss?.invoke()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            style = MaterialTheme.typography.titleMedium
                                .copy(color = Color.Blue, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        )
    }
}
