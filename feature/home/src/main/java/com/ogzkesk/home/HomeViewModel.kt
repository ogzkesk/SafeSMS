package com.ogzkesk.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.core.tflite.TextClassificationClient
import com.ogzkesk.core.util.SmsUtils
import com.ogzkesk.domain.model.SmsMessage
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
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMessages: FetchMessages,
    private val insertMessages: InsertMessages,
    private val insertContacts: InsertContacts
) : ViewModel() {


    private var _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()


    fun initResources(context: Context) {

        Timber.d("fetchMessagesFromContent()")
        val sms = SmsUtils.readSms(context)
        val contact = SmsUtils.readContacts(context)

        viewModelScope.launch(Dispatchers.IO) {
            if (sms.isNotEmpty()) {
                Timber.d("sms.isNotEmpty()")
                insertMessages(classifyMessages(context, sms))
            }
            if(contact.isNotEmpty()){
                Timber.d("contact.isNotEmpty()")
                insertContacts(contact)
            }
        }
    }

    private fun classifyMessages(
        context: Context,
        messages: List<SmsMessage>,
    ): List<SmsMessage> {

        Timber.d("classifyMessages()")

        val client = TextClassificationClient(context).apply { load() }
        val classifiedSms = mapMessages(messages, client)
        client.unload()
        return classifiedSms
    }


    fun removeMessages(context: Context, threadId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        SmsUtils.removeSms(context, threadId)
        initResources(context)
    }

    fun onNavigate(route: String) {
        viewModelScope.launch {
            _event.send(HomeEvent.Navigate(route))
        }
    }

    private fun mapMessages(
        messages: List<SmsMessage>,
        client: TextClassificationClient,
    ): List<SmsMessage> {

        Timber.d("mapMessages()")

        return messages.map { sms ->

            val category = client.classify(sms.message)

            if (category.isEmpty() || category.size < 2) {
                Timber.d("category.isEmpty || category.size < 2 category.size = ${category.size}")
                return@map sms
            }

            val score = category[1].score
            if (score >= 0.6f) {

                Timber.d(
                    "Spam Detected : score = $score message = ${sms.message}"
                )

                SmsMessage(
                    isSpam = true,
                    isFav = false,
                    isRead = false,
                    message = sms.message,
                    sender = sms.sender,
                    date = sms.date,
                    thread = sms.thread,
                    id = sms.id
                )
            } else {
                Timber.d("Not spam score = $score message = ${sms.message}")
                sms
            }
        }
    }


    private fun initMessages() {
        fetchMessages().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Error -> {
                    _event.send(
                        HomeEvent.Error(resource.message ?: "")
                    )
                }

                is Resource.Success -> {

                    val data = resource.data ?: return@onEach
                    _uiState.update { state ->
                        state.copy(
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


    init {
        initMessages()
    }
}

