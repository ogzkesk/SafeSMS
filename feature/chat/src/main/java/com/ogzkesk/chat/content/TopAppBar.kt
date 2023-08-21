package com.ogzkesk.chat.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.R
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage

@Composable
internal fun TopAppBar(
    data: List<SmsMessage>,
    selectedContacts: List<Contact>,
    contactText: String,
    focusRequester: FocusRequester,
    onContactTextChanged: (String) -> Unit,
    onRemoveContact: (Contact) -> Unit,
    onNavigate: (String?) -> Unit,
    onCall: (String) -> Unit,
) {
    if (data.isNotEmpty()) {
        SmallTopBar(
            data = data.first(),
            onNavigate = onNavigate,
            onCall = onCall
        )
    } else {
        LargeTopAppBar(
            value = contactText,
            selectedContacts = selectedContacts,
            onValueChanged = onContactTextChanged,
            onRemoveContact = onRemoveContact,
            focusRequester = focusRequester,
            onNavigate = onNavigate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SmallTopBar(
    data: SmsMessage,
    onNavigate: (String?) -> Unit,
    onCall: (String) -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = data.name.ifEmpty { data.sender },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigate(null) }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onCall(data.sender) }) {
                Icon(
                    imageVector = Icons.Rounded.Phone,
                    contentDescription = null
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LargeTopAppBar(
    value: String,
    selectedContacts: List<Contact>,
    onValueChanged: (String) -> Unit,
    onRemoveContact: (Contact) -> Unit,
    onNavigate: (String?) -> Unit,
    focusRequester: FocusRequester,
) {

    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.new_message),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { onNavigate(null) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 8.dp),
        ) {
            items(
                items = selectedContacts,
                key = { it.id },
                itemContent = { contact ->
                    ElevatedSuggestionChip(
                        onClick = { onRemoveContact(contact) },
                        label = {
                            Text(
                                text = contact.name,
                                style = MaterialTheme.typography.bodySmall
                                    .copy(fontWeight = FontWeight.Light)
                            )
                        }
                    )
                }
            )

            item {
                TextField(
                    value = value,
                    onValueChange = onValueChanged,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.to),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray.copy(alpha = 0.7f)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        IconButton(onClick = { onNavigate(Routes.Contacts.route) }) {
                            Icon(
                                imageVector = Icons.Outlined.PersonAddAlt,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray.copy(alpha = 0.6f))
        )
    }
}