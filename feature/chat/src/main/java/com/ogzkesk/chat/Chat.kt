package com.ogzkesk.chat

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ogzkesk.chat.content.BottomAppBar
import com.ogzkesk.chat.content.TopAppBar
import com.ogzkesk.chat.content.scaffold.messageSection
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState

@Composable
fun Chat(
    id: Int?,
    onPopBackstack: () -> Unit,
    onNavigate: (String) -> Unit,
) {

    val viewModel: ChatViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val messageText by viewModel.messageText
    val errorDialogState = remember { ErrorDialogState() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is ChatEvent.Error -> {
                    errorDialogState.showErrorDialog(event.message)
                }

                is ChatEvent.Navigate -> {
                    event.route?.let(onNavigate) ?: onPopBackstack()
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {

        focusRequester.requestFocus()

        if (id != null) {
            viewModel.fetchSmsByThreadId(id)
        }
    }


    Scaffold(
        topBar = {
            if (state.data.isNotEmpty()) {
                // TODO for else case make sender ui
                TopAppBar(
                    title = state.data.first().sender,
                    onNavigate = viewModel::onNavigate,
                    onCall = {}
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                value = messageText,
                onValueChanged = viewModel::onMessageTextChanged,
                focusRequester = focusRequester,
                onSendClick = { },
            )
        }
    ) { padd ->

        LazyColumn(
            contentPadding = padd,
            content = {
                messageSection(state.data)
            }
        )

        ErrorDialog(errorDialogState)

    }
}