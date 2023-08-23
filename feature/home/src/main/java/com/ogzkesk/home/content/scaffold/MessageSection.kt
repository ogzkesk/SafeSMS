package com.ogzkesk.home.content.scaffold

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.ogzkesk.domain.model.SmsMessage

internal fun LazyListScope.messageSection(
    items: List<SmsMessage>,
    onMessageClicked: (arg: String) -> Unit,
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