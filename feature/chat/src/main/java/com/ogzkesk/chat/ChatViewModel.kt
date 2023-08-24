package com.ogzkesk.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.chat.content.mapAsSeen
import com.ogzkesk.core.R
import com.ogzkesk.core.ext.flowOnIO
import com.ogzkesk.core.ext.launchIO
import com.ogzkesk.core.ext.reset
import com.ogzkesk.core.util.Constants.EMPTY
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.use_case.FetchMessages
import com.ogzkesk.domain.use_case.InsertMessages
import com.ogzkesk.domain.use_case.Preferences
import com.ogzkesk.domain.use_case.QueryContacts
import com.ogzkesk.domain.use_case.UpdateMessages
import com.ogzkesk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val fetchMessages: FetchMessages,
    private val updateMessages: UpdateMessages,
    private val queryContacts: QueryContacts,
    private val insertMessages: InsertMessages,
    private val preferences: Preferences,
) : ViewModel() {

    private val _event = Channel<ChatEvent>()
    val event = _event.receiveAsFlow()

    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()

    private val phoneNumber : StateFlow<String> = preferences.readPhoneNumber
        .flowOnIO()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = EMPTY
        )

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

        contactText.reset()
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

        viewModelScope.launch {
            _event.send(ChatEvent.ContactSelected)
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

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            data = data.sortedBy { it.date }.reversed()
                        )
                    }
                }
            }
        }
            .flowOnIO()
            .launchIn(viewModelScope)
    }

    private fun updateAsSeen(data: List<SmsMessage>) {

        if (data.isEmpty()) return
        val isSeen = data.any { it.isRead }

        viewModelScope.launchIO {
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
            .flowOnIO()
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
            .flowOnIO()
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

        messageText.reset()
        insertSendSms(contacts,sms)

        viewModelScope.launch {
            _event.send(ChatEvent.SendSms(contacts,sms))
        }
    }

    private fun insertSendSms(contacts: List<Contact>, sms: String){

        val messages = contacts.map { contact ->
            SmsMessage(
                isRead = true,
                isFav = false,
                isSpam = false,
                type = SmsMessage.SENT,
                message = sms,
                sender = contact.number,
                name = contact.name,
                date = System.currentTimeMillis(),
                id = UUID.randomUUID().mostSignificantBits
            )
        }

        viewModelScope.launchIO {
            insertMessages(messages)
        }
    }


    fun onCall(number: String){
        viewModelScope.launch {
            _event.send(ChatEvent.Call(number))
        }
    }
}