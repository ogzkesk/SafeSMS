package com.ogzkesk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    val isSpam : Boolean,
    val isFav : Boolean,
    val isRead: Boolean,
    val type: String,
    val name : String,
    val message: String,
    val sender: String,
    val date: Long,
    @PrimaryKey val id: Long,
)