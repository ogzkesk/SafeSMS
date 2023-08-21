package com.ogzkesk.home

import androidx.compose.runtime.Stable
import com.ogzkesk.domain.model.SmsMessage

@Stable
data class HomeState(
    val isLoading: Boolean = false,
    val data : List<SmsMessage> = emptyList()
)
