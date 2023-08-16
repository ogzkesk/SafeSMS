package com.ogzkesk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ogzkesk.data.util.Constants.MESSAGE_TABLE

@Entity
data class MessageEntity(
    val isSpam : Boolean,
    val isFav : Boolean,
    val message: String,
    val sender: String,
    val date: Long,
    val thread: Int,
    @PrimaryKey val id: Long,
)