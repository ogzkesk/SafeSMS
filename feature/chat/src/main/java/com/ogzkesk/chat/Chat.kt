package com.ogzkesk.chat

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ogzkesk.chat.content.BottomAppBar
import com.ogzkesk.chat.content.DEFAULT_ARG
import com.ogzkesk.chat.content.TopAppBar
import com.ogzkesk.chat.content.scaffold.contactSection
import com.ogzkesk.chat.content.scaffold.messageSection
import com.ogzkesk.chat.content.sendSms
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.core.util.Constants.CALL_ACTION

@Composable
fun Chat(
    id: Int,
    onPopBackstack: () -> Unit,
    onNavigate: (String) -> Unit,
) {

    val context = LocalContext.current
    val viewModel: ChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val messageText by viewModel.messageText
    val contactText by viewModel.contactText
    val errorDialogState = remember { ErrorDialogState() }
    val messageFocusRequester = remember { FocusRequester() }
    val contactFocusRequester = remember { FocusRequester() }


    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is ChatEvent.Error -> {
                    event.message?.let(errorDialogState::showErrorDialog) ?:
                    event.resId?.let(errorDialogState::showErrorDialog)
                }

                is ChatEvent.Navigate -> {
                    event.route?.let(onNavigate) ?: onPopBackstack()
                }

                is ChatEvent.Call -> {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(CALL_ACTION + event.number)
                    context.startActivity(intent)
                }

                is ChatEvent.SendSms -> {
                    sendSms(context,event.contacts,event.message)
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {

        if (id != DEFAULT_ARG) {
            messageFocusRequester.requestFocus()
            viewModel.fetchSmsByThreadId(id)
        } else {
            contactFocusRequester.requestFocus()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                data = state.data,
                selectedContacts = state.selectedContacts,
                contactText = contactText,
                focusRequester = contactFocusRequester,
                onContactTextChanged = viewModel::onContactTextChanged,
                onRemoveContact = viewModel::onRemoveContact,
                onNavigate = viewModel::onNavigate,
                onCall = viewModel::onCall
            )
        },
        bottomBar = {
            BottomAppBar(
                value = messageText,
                selectedContacts = state.selectedContacts,
                onValueChanged = viewModel::onMessageTextChanged,
                focusRequester = messageFocusRequester,
                onSendClick = viewModel::onSendSms,
            )
        }
    ) { padd ->

        LazyColumn(
            contentPadding = padd,
            content = {

                messageSection(state.data)

                contactSection(
                    state.contacts,
                    viewModel::onContactSelected
                )
            }
        )

        ErrorDialog(errorDialogState)

    }
}