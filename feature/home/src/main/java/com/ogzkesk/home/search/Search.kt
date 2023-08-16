package com.ogzkesk.home.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ogzkesk.core.R
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState
import com.ogzkesk.home.HomeEvent
import com.ogzkesk.home.content.messageSection


@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            TopAppBar(
                title = {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .focusRequester(focusRequester),
                            value = searchText,
                            onValueChange = { viewModel.onSearchTextChanged(it) },
                            singleLine = true,
                            maxLines = 1
                        )

                        if (searchText.isNotEmpty()) {
                            IconButton(
                                modifier = Modifier
                                    .size(24.dp),
                                onClick = {
                                    viewModel.onSearchTextChanged("")
                                    focusManager.clearFocus()
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
                actions = {

                    TextButton(
                        colors = ButtonDefaults
                            .textButtonColors(contentColor = Color.Black.copy(alpha = 0.8f)),
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.onNavigate(null)
                        },
                        content = {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(fontWeight = FontWeight.SemiBold),
                            )
                        }
                    )
                }
            )
        }
    ) { padd ->
        LazyColumn(
            contentPadding = padd,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {

                item {
                    if (uiState.result.any { !it.isSpam }) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Messages"
                        )
                    }
                }

                messageSection(uiState.result.filter { !it.isSpam }) {
                    // TODO on click to messsage ..
                }

                item {
                    if (uiState.result.any { it.isSpam }) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Spams"
                        )
                    }
                }

                messageSection(uiState.result.filter { it.isSpam }) {
                    // TODO on click to messsage ..
                }
            }
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
                content = {
                    CircularProgressIndicator()
                }
            )
        }

        ErrorDialog(errorState)
    }
}


