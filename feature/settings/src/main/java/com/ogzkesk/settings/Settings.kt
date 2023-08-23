package com.ogzkesk.settings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Settings(onNavigateUp: () -> Unit) {

    val viewModel: SettingsViewModel = hiltViewModel()


}