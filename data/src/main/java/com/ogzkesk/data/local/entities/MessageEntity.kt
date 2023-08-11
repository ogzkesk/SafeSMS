package com.ogzkesk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ogzkesk.data.util.Constants.MESSAGE_TABLE

@Entity(tableName = MESSAGE_TABLE)
data class MessageEntity(
    val isSpam : Boolean,
    val message: String,
    val sender: String,
    val date: Long,
    val thread: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Long,
)