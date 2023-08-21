package com.ogzkesk.core.base

import com.ogzkesk.domain.util.Resource

class ResourceWrapper<T>() {

    // TODO migrate later..

    fun wrap(
        resource: Resource<List<T>>,
        onLoading: (() -> Unit)? = null,
        onError: ((message: String) -> Unit)? = null,
        onSuccess: ((data: List<T>) -> Unit)? = null
    ): UiState<T> {
        return when (resource) {

            is Resource.Loading -> {
                onLoading?.invoke()
                val data = resource.data ?: emptyList()
                UiState(
                    isLoading = false,
                    data = data
                )
            }

            is Resource.Error -> {
                resource.message?.let { onError?.invoke(it) }
                UiState(
                    isLoading = false,
                    data = emptyList()
                )
            }

            is Resource.Success -> {
                val data = resource.data ?: emptyList()
                onSuccess?.invoke(data)
                UiState(
                    isLoading = false,
                    data = data
                )
            }
        }
    }
}