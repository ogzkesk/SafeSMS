package com.ogzkesk.home

import com.ogzkesk.domain.model.SmsMessage

data class HomeState(
    val isLoading: Boolean = true,
    val result : List<SmsMessage> = emptyList()
)
