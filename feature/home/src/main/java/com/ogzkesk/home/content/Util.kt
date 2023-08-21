package com.ogzkesk.home.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ogzkesk.core.R
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.home.HomeState

@Composable
internal fun mapTitle(index: Int,inboxSize: Int, spamSize: Int) : String {
    return if (index == 0) {
        stringResource(id = R.string.inbox, inboxSize)
    } else {
        stringResource(id = R.string.spam, spamSize)
    }
}

internal fun mapMessages(screenIndex: Int, uiState: HomeState): List<SmsMessage> {
    return when (screenIndex) {
        0 -> uiState.data.convertAsConversation().filter { !it.isSpam }
        1 -> uiState.data.convertAsConversation().filter { it.isSpam }
        else -> {
            uiState.data
        }
    }
}

internal fun getInboxSize(message: List<SmsMessage>) : Int {
    return message.convertAsConversation().filter { !it.isSpam }.size
}

internal fun getSpamSize(message: List<SmsMessage>) : Int {
    return message.convertAsConversation().filter { it.isSpam }.size
}

internal fun List<SmsMessage>.convertAsConversation(): List<SmsMessage>{

    return distinctBy { it.sender }
}

