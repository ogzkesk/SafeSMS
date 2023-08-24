package com.ogzkesk.chat

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ogzkesk.chat.content.BottomAppBar
import com.ogzkesk.chat.content.TopAppBar
import com.ogzkesk.chat.content.scaffold.contactSection
import com.ogzkesk.chat.content.scaffold.messageSection
import com.ogzkesk.chat.content.sendSms
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.core.util.Constants.CALL_ACTION
import timber.log.Timber

@Composable
fun Chat(
    sender: String,
    onNavigateToContacts: () -> Unit,
    onNavigateUp: () -> Unit,
) {

    val context = LocalContext.current
    val viewModel: ChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val messageText by viewModel.messageText
    val contactText by viewModel.contactText
    val errorDialogState = remember { ErrorDialogState() }
    val messageFocusRequester = remember { FocusRequester() }
    val contactFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()


    LaunchedEffect(key1 = state.data){
        if(lazyListState.canScrollBackward){
            lazyListState.scrollToItem(0)
        }
    }


    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {

                is ChatEvent.Error -> {
                    event.message?.let(errorDialogState::showErrorDialog) ?: event.resId?.let(
                        errorDialogState::showErrorDialog
                    )
                }

                is ChatEvent.NavigateToContacts -> {
                    onNavigateToContacts()
                }

                is ChatEvent.NavigateUp -> {
                    focusManager.clearFocus()
                    onNavigateUp()
                }

                is ChatEvent.Call -> {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(CALL_ACTION + event.number)
                    context.startActivity(intent)
                }

                is ChatEvent.SendSms -> {
                    sendSms(context, event.contacts, event.message)
                }

                is ChatEvent.ContactSelected -> {
                    messageFocusRequester.requestFocus()
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {

        if (sender != Routes.Chat.DEFAULT_ARG) {
            Timber.d("fetchSmsBySender()")
            messageFocusRequester.requestFocus()
            viewModel.fetchSmsBySender(sender)
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
                onNavigateToContacts = viewModel::onNavigateToContacts,
                onRemoveContact = viewModel::onRemoveContact,
                onNavigateUp = viewModel::onNavigateUp,
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
            state = lazyListState,
            contentPadding = padd,
            reverseLayout = true,
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