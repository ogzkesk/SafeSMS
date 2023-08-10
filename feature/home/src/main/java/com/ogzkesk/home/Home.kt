package com.ogzkesk.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Home(onNavigate: (String) -> Unit) {

    val viewModel : HomeViewModel = hiltViewModel()

}