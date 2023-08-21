package com.ogzkesk.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(): ViewModel() {

    private val _event = Channel<ContactEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow(ContactState())
    val uiState = _uiState.asStateFlow()


    fun onNavigate(route: String?){
        viewModelScope.launch {
            _event.send(ContactEvent.Navigate(route))
        }
    }

    fun testError(message: String){
        viewModelScope.launch {
            _event.send(ContactEvent.Error(message))
        }
    }
}