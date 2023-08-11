package com.ogzkesk.core.ui.component
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


@Composable
fun ErrorDialog(
    errorDialogState: Boolean,
    onDismiss: () -> Unit
) {

    if (errorDialogState) {

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
                        text = stringResource(id = R.string.permission_declined),
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
                            .clickable { onDismiss() }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.titleMedium
                                .copy(color = Color.Blue, fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        )
    }
}
