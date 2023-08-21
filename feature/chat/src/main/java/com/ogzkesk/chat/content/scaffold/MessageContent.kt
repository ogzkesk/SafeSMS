package com.ogzkesk.chat.content.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.util.formatDate
import com.ogzkesk.domain.model.SmsMessage

@Composable
internal fun MessageContent(message: SmsMessage) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {

            Text(
                text = message.date.formatDate(),
                style = MaterialTheme.typography.bodySmall
                    .copy(fontWeight = FontWeight.Light)
            )

            when(message.type){
                SmsMessage.RECEIVED -> ReceivedMessageBox(message = message)
                SmsMessage.SENT -> SentMessageBox(message = message)
            }
        }
    )
}

@Composable
private fun ColumnScope.ReceivedMessageBox(message: SmsMessage) {
    Box(
        modifier = Modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp)
            .padding(end = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        content = {
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    )
}

@Composable
private fun ColumnScope.SentMessageBox(message: SmsMessage) {
    Box(
        modifier = Modifier
            .align(Alignment.End)
            .padding(horizontal = 16.dp)
            .padding(start = 24.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    topEnd = 16.dp,
                    topStart = 16.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary),
        content = {
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    )
}
