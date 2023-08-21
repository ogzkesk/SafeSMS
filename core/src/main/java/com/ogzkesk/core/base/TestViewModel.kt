package com.ogzkesk.core.base

import androidx.lifecycle.ViewModel
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.repository.SmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach

class TestViewModel(private val smsRepository: SmsRepository) : ViewModel() {

    private val resourceWrapper = ResourceWrapper<SmsMessage>()

    private val _uiState = MutableStateFlow(UiState<SmsMessage>())
    val uiState get() = _uiState.asStateFlow()

    fun getSmsMessages() {
        smsRepository.fetchSms().onEach { resource ->
            _uiState.value = resourceWrapper.wrap(resource)
        }
    }
}