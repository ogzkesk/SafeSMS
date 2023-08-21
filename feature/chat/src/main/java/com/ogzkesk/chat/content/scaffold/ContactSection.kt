package com.ogzkesk.chat.content.scaffold

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.ogzkesk.domain.model.Contact

internal fun LazyListScope.contactSection(
    contacts: List<Contact>,
    onContactClicked: (Contact) -> Unit,
) {

    items(
        items = contacts,
        key = { it.id },
        itemContent = { contact ->
            ContactContent(
                contact = contact,
                onContactClicked = onContactClicked
            )
        }
    )
}