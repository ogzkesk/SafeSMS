package com.ogzkesk.contact

import androidx.compose.runtime.Stable
import com.ogzkesk.domain.model.Contact

@Stable
data class ContactState(
    val isLoading: Boolean = false,
    val data: List<Contact> = emptyList()
)