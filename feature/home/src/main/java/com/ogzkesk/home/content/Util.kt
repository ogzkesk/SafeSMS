package com.ogzkesk.home.content

import androidx.compose.runtime.Composable
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.home.HomeState

@Composable
internal fun mapMessages(screenIndex: Int, uiState: HomeState): List<SmsMessage> {
    return when (screenIndex) {
        0 -> uiState.result.filter { !it.isSpam }
        1 -> uiState.result.filter { it.isSpam }
        else -> {
            uiState.result
        }
    }
}