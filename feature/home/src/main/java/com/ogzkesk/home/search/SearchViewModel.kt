package com.ogzkesk.home.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.domain.use_case.QueryMessages
import com.ogzkesk.domain.util.Resource
import com.ogzkesk.home.HomeEvent
import com.ogzkesk.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val queryMessages: QueryMessages
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()

    var searchText = mutableStateOf("")
        private set


    fun onSearchTextChanged(query: String) {
        searchText.value = query
        querySms()
    }

    private fun querySms(){

        if(searchText.value.isEmpty()){
            _uiState.update { it.copy(data = emptyList()) }
            return
        }

        queryMessages(searchText.value).onEach { resource ->
            when(resource){
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _event.send(HomeEvent.Error(resource.message ?: ""))
                }
                is Resource.Success -> {
                    val data = resource.data ?: _uiState.value.data
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            data = data.sortedBy { it.date }.reversed()
                        )
                    }
                }
            }
        }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onNavigateToChat(arg: String?) {
        viewModelScope.launch {
            _event.send(
                HomeEvent.NavigateToChat(arg)
            )
        }
    }

    fun onNavigateUp(){
        viewModelScope.launch {
            _event.send(
                HomeEvent.NavigateUp
            )
        }
    }
}