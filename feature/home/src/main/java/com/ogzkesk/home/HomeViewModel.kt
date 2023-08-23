package com.ogzkesk.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.core.sms.ClassificationHelper
import com.ogzkesk.core.sms.SmsUtils
import com.ogzkesk.core.util.mapSenderName
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.use_case.FetchMessages
import com.ogzkesk.domain.use_case.InsertContacts
import com.ogzkesk.domain.use_case.InsertMessages
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
class HomeViewModel @Inject constructor(
    private val fetchMessages: FetchMessages,
    private val insertMessages: InsertMessages,
    private val insertContacts: InsertContacts,
) : ViewModel() {


    private lateinit var classification: ClassificationHelper

    private var _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()


    fun initResources(context: Context) {

        classification = ClassificationHelper(context)

        viewModelScope.launch(Dispatchers.IO) {
            val contacts = SmsUtils.readContacts(context)
            insertContacts(contacts)
            initMessages(context,contacts)
        }
    }

    private fun initMessages(context: Context,contacts: List<Contact>) {
        fetchMessages().onEach { resource ->

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
                        HomeEvent.Error(resource.message ?: "")
                    )

                    insertMessages(
                        classification.classifyMessages(
                            SmsUtils.readReceivedSms(context)
                                .mapSenderName(contacts)
                        )
                    )
                }

                is Resource.Success -> {
                    val data = resource.data ?: emptyList()

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            data = data
                        )
                    }

                    if(data.isEmpty()){
                        insertMessages(
                            classification.classifyMessages(
                                SmsUtils.readReceivedSms(context)
                                    .mapSenderName(contacts)
                            )
                        )
                    }
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }


    fun removeMessages(context: Context, threadId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        SmsUtils.removeSms(context, threadId)
        initResources(context)
    }

    fun onNavigateToChat(route: String?) {
        viewModelScope.launch {
            _event.send(HomeEvent.NavigateToChat(route))
        }
    }

    fun onNavigateToSearch(){
        viewModelScope.launch {
            _event.send(HomeEvent.NavigateToSearch)
        }
    }

    fun onNavigateToSettings(){
        viewModelScope.launch {
            _event.send(HomeEvent.NavigateToSettings)
        }
    }
}

