package com.ogzkesk.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.chat.content.mapAsSeen
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.use_case.FetchMessages
import com.ogzkesk.domain.use_case.UpdateMessages
import com.ogzkesk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val fetchMessages: FetchMessages,
    private val updateMessages: UpdateMessages
) : ViewModel() {

    private val _event = Channel<ChatEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()

    var messageText = mutableStateOf("")
        private set

    fun onMessageTextChanged(query: String) {
        messageText.value = query
    }

    // TODO set new topbar finally.. add new screen to multiple contact selection.

    fun fetchSmsByThreadId(threadId: Int) {

        fetchMessages.fetchByThreadId(threadId).onEach { resource ->

            when (resource) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.send(ChatEvent.Error(resource.message ?: ""))
                }

                is Resource.Success -> {
                    val data = resource.data ?: emptyList()

                    updateAsSeen(data)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = data
                        )
                    }
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun updateAsSeen(data: List<SmsMessage>){

        if(data.isEmpty()) return
        val isSeen = data.any { it.isRead }

        viewModelScope.launch(Dispatchers.IO) {
            if(!isSeen){
                updateMessages(data.mapAsSeen())
            }
        }
    }

    fun onNavigate(route: String?) {
        viewModelScope.launch {
            _event.send(ChatEvent.Navigate(route))
        }
    }
}