package com.ogzkesk.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ogzkesk.core.R
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.core.ui.navigation.Routes
import com.ogzkesk.home.content.TabSection
import com.ogzkesk.home.content.mapMessages
import com.ogzkesk.home.content.messageSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onNavigate: (String) -> Unit) {

    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val errorState = remember { ErrorDialogState() }
    val screenIndex = remember { mutableStateOf(0) }
    val appBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchMessagesFromContent(context)
    }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.Error -> {
                    errorState.showErrorDialog(
                        event.message
                    )
                }

                is HomeEvent.Navigate -> {
                    event.route?.let(onNavigate)
                }
            }
        }
    }


    val messages = mapMessages(
        screenIndex = screenIndex.value,
        uiState = uiState
    )

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = appBarBehavior,
                title = { Text(text = stringResource(id = R.string.home)) },
                actions = {

                    IconButton(onClick = { viewModel.onNavigate(Routes.Search.route) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = { onNavigate(Routes.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padd ->

        Column(
            modifier = Modifier
                .padding(padd)
                .nestedScroll(appBarBehavior.nestedScrollConnection)
        ) {

            TabSection(
                selectedIndex = screenIndex.value,
                onIndexChanged = { screenIndex.value = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp),
                content = {
                    messageSection(messages) { /* TODO onMessageClicked  */ }
                }
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        CircularProgressIndicator()
                    }
                )
            }

            ErrorDialog(errorState)
        }
    }
}

