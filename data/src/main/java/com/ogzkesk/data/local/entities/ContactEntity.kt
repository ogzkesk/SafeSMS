package com.ogzkesk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val number: String
)