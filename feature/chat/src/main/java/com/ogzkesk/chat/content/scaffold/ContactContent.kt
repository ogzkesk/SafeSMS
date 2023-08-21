package com.ogzkesk.chat.content.scaffold

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.ui.theme.randomColorList
import com.ogzkesk.domain.model.Contact

@Composable
internal fun ContactContent(contact: Contact, onContactClicked: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
            .clickable { onContactClicked(contact) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(randomColorList.random())
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.name.first().toString(),
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = contact.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
                    .copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = contact.number,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
                    .copy(fontWeight = FontWeight.Light)
            )
        }
    }
}