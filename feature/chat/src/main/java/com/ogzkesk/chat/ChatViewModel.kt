package com.ogzkesk.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.chat.content.mapAsSeen
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.use_case.FetchMessages
import com.ogzkesk.domain.use_case.QueryContacts
import com.ogzkesk.domain.use_case.UpdateMessages
import com.ogzkesk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.ogzkesk.core.R
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val fetchMessages: FetchMessages,
    private val updateMessages: UpdateMessages,
    private val queryContacts: QueryContacts
) : ViewModel() {

    private val _event = Channel<ChatEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()

    var messageText = mutableStateOf("")
        private set

    var contactText = mutableStateOf("")
        private set


    fun onMessageTextChanged(query: String) {
        messageText.value = query
    }


    fun onContactTextChanged(query: String) {
        contactText.value = query
        queryContacts()
    }

    fun onRemoveContact(contact: Contact){
        val selectedContacts = mutableListOf<Contact>()
        selectedContacts.addAll(uiState.value.selectedContacts)
        selectedContacts.remove(contact)
        _uiState.update {
            it.copy(selectedContacts = selectedContacts.toList())
        }
    }


    fun onContactSelected(contact: Contact) {

        contactText.value = ""
        val selectedContacts = mutableListOf<Contact>()
        selectedContacts.addAll(uiState.value.selectedContacts)

        if(selectedContacts.contains(contact)){
            _event.trySend(
                ChatEvent.Error(null,R.string.contact_exists)
            )
            return
        }

        selectedContacts.add(contact)

        _uiState.update {
            it.copy(
                contacts = emptyList(),
                selectedContacts = selectedContacts.toList()
            )
        }
    }

    fun fetchSmsBySender(sender: String) {

        Timber.d("sender ::$sender")

        fetchMessages.fetchBySender(sender).onEach { resource ->

            when (resource) {
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _event.send(
                        ChatEvent.Error(resource.message,R.string.def_error_message)
                    )
                }

                is Resource.Success -> {

                    val data = resource.data ?: emptyList()
                    updateAsSeen(data)
                    getContactByNumber(data)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = data.reversed()
                        )
                    }
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun updateAsSeen(data: List<SmsMessage>) {

        if (data.isEmpty()) return
        val isSeen = data.any { it.isRead }

        viewModelScope.launch(Dispatchers.IO) {
            if (!isSeen) {
                updateMessages(data.mapAsSeen())
            }
        }
    }

    private fun getContactByNumber(data: List<SmsMessage>){
        if(data.isEmpty()) return

        queryContacts(data.first().sender).onEach { resource ->
            val contacts = resource.data ?: emptyList()
            Timber.d("contacts : $contacts")
            _uiState.update {
                it.copy(
                    isLoading = false,
                    selectedContacts = contacts
                )
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun queryContacts() {

        if (contactText.value.isEmpty()) {
            _uiState.update { it.copy(contacts = emptyList()) }
            return
        }

        queryContacts(contactText.value).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _event.send(
                        ChatEvent.Error(resource.message, R.string.def_error_message)
                    )
                }

                is Resource.Success -> {
                    val data = resource.data ?: return@onEach
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            contacts = data.take(10)
                        )
                    }
                }
            }
        }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onNavigateToContacts() {
        viewModelScope.launch {
            _event.send(ChatEvent.NavigateToContacts)
        }
    }

    fun onNavigateUp(){
        viewModelScope.launch {
            _event.send(ChatEvent.NavigateUp)
        }
    }

    fun onSendSms(sms: String,contacts: List<Contact>){
        viewModelScope.launch {
            _event.send(
                ChatEvent.SendSms(contacts,sms)
            )
        }
    }


    fun onCall(number: String){
        viewModelScope.launch {
            _event.send(ChatEvent.Call(number))
        }
    }
}