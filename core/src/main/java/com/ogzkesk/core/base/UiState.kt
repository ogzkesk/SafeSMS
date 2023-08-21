package com.ogzkesk.core.base

import androidx.compose.runtime.Stable

@Stable
data class UiState<T>(
    val isLoading: Boolean = false,
    val data: List<T> = emptyList()
)
