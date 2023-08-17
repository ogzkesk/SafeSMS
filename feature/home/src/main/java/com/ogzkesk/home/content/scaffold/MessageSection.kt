package com.ogzkesk.home.content.scaffold

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ogzkesk.domain.model.SmsMessage

internal fun LazyListScope.messageSection(
    items: List<SmsMessage>,
    onMessageClicked: (String) -> Unit,
) {

    items(
        items = items,
        key = { it.id },
        itemContent = {
            MessageItemContent(
                message = it,
                onMessageClicked = onMessageClicked
            )
        }
    )
}