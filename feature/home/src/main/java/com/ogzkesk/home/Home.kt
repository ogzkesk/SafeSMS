package com.ogzkesk.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.core.ui.component.LoadingContent
import com.ogzkesk.home.content.FloatActionButton
import com.ogzkesk.home.content.TopBar
import com.ogzkesk.home.content.getInboxSize
import com.ogzkesk.home.content.getSpamSize
import com.ogzkesk.home.content.mapMessages
import com.ogzkesk.home.content.scaffold.TabSection
import com.ogzkesk.home.content.scaffold.messageSection
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onNavigateToChat: (arg: String?) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToSearch: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val errorState = remember { ErrorDialogState() }
    val screenIndex = remember { mutableIntStateOf(0) }
    val isSmsFetched = rememberSaveable { mutableStateOf(false) }
    val appBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        if (!isSmsFetched.value) {
            Timber.d("fetchingSmsFromResolver()")
            viewModel.initResources(context)
            isSmsFetched.value = true
        }
    }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.Error -> {
                    errorState.showErrorDialog(
                        event.message
                    )
                }

                is HomeEvent.NavigateToChat -> {
                    onNavigateToChat(event.arg)
                }

                is HomeEvent.NavigateToSearch -> {
                    onNavigateToSearch()
                }

                is HomeEvent.NavigateToSettings -> {
                    onNavigateToSettings()
                }

                is HomeEvent.NavigateUp -> {

                }
            }
        }
    }

    val messages = mapMessages(
        screenIndex = screenIndex.intValue,
        uiState = uiState
    )

    Scaffold(
        topBar = {
            TopBar(
                appBarBehavior,
                viewModel::onNavigateToSearch,
                viewModel::onNavigateToSettings
            )
        },
        floatingActionButton = {
            FloatActionButton(viewModel::onNavigateToChat)
        }
    ) { padd ->

        Column(
            modifier = Modifier
                .padding(padd)
                .nestedScroll(appBarBehavior.nestedScrollConnection)
        ) {

            TabSection(
                inboxSize = getInboxSize(uiState.data),
                spamSize = getSpamSize(uiState.data),
                selectedIndex = screenIndex.intValue,
                onIndexChanged = { screenIndex.intValue = it }
            )

            LazyColumn(state = lazyListState) {
                messageSection(messages, viewModel::onNavigateToChat)
            }

            LoadingContent(uiState.isLoading)

            ErrorDialog(errorState)
        }
    }
}

