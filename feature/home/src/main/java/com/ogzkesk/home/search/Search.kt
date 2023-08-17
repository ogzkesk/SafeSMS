package com.ogzkesk.home.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ogzkesk.core.R
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.core.ui.component.LoadingContent
import com.ogzkesk.home.HomeEvent
import com.ogzkesk.home.content.scaffold.messageSection
import com.ogzkesk.home.search.scaffold.SearchTopBar
import com.ogzkesk.home.search.scaffold.messageTitle


@Composable
fun Search(
    onPopBackstack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val viewModel: SearchViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val searchText by viewModel.searchText
    val errorState = remember { ErrorDialogState() }


    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {

                is HomeEvent.Navigate -> {
                    event.route?.let(onNavigate) ?: onPopBackstack()
                }

                is HomeEvent.Error -> {
                    errorState.showErrorDialog(
                        R.string.permission_declined
                    )
                }
            }
        }
    }


    Scaffold(
        contentWindowInsets = WindowInsets.ime,
        topBar = {
            SearchTopBar(
                focusRequester = focusRequester,
                focusManager = focusManager,
                searchText = searchText,
                onSearchTextChanged = viewModel::onSearchTextChanged,
                onNavigate = viewModel::onNavigate
            )
        }
    ) { padd ->

        LazyColumn(
            contentPadding = padd,
            content = {

                messageTitle(
                    R.string.messages,
                    uiState.data.any{ !it.isSpam }
                )

                messageSection(
                    uiState.data.filter { !it.isSpam },
                    viewModel::onNavigate
                )

                messageTitle(
                    R.string.spams,
                    uiState.data.any{ it.isSpam }
                )

                messageSection(
                    uiState.data.filter { it.isSpam },
                    viewModel::onNavigate
                )
            }
        )

        LoadingContent(uiState.isLoading)

        ErrorDialog(errorState)
    }
}


