package com.ogzkesk.contact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ogzkesk.core.ui.component.ErrorDialog
import com.ogzkesk.core.ui.component.ErrorDialogState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contact(onNavigateUp: () -> Unit) {

    val viewModel: ContactViewModel = hiltViewModel()
    val errorDialogState = remember { ErrorDialogState() }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {

                is ContactEvent.Error -> {
                    errorDialogState.showErrorDialog(event.message)
                }

                is ContactEvent.NavigateUp -> {
                    onNavigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = viewModel::onNavigateUp) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padd ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(padd), Alignment.Center) {
            Button(onClick = { viewModel.testError("DENEME ERRORDUR !") }) {
                Text(text = "Test Error")
            }
        }
        ErrorDialog(state = errorDialogState)
    }
}