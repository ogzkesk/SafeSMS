package com.ogzkesk.home.content.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.core.ui.theme.PurpleGrey80
import com.ogzkesk.core.ui.theme.randomColorList
import com.ogzkesk.core.util.getElapsedTime
import com.ogzkesk.domain.model.SmsMessage
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MessageItemContent(
    message: SmsMessage,
    onMessageClicked: (arg: String) -> Unit,
) {


    Card(
        colors = CardDefaults.cardColors(Color.White),
        onClick = {
            onMessageClicked(message.sender)
        }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 8.dp
            )
        ) {

            CardIcon(name = message.name)

            CardContent(message = message)
        }
    }
}

@Composable
private fun CardContent(message: SmsMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            if (!message.isRead) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(8.dp)
                        .background(Color.Red)
                )
            }

            Text(
                text = message.name.ifEmpty { message.sender },
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = message.date.getElapsedTime(LocalContext.current),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Blue
            )
        }

        Text(
            text = message.message,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Composable
private fun CardIcon(name: String) {
    if (name.isEmpty()) {

        Icon(
            imageVector = Icons.Filled.Person2,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(PurpleGrey80)
                .padding(8.dp)
        )

    } else {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(randomColorList.random())
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}