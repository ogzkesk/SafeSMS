package com.ogzkesk.chat

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Chat(
    onPopBackstack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val viewModel: ChatViewModel = hiltViewModel()


}