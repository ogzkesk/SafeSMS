package com.ogzkesk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ogzkesk.data.util.Constants.MESSAGE_ENTITY_NAME

@Entity(tableName = MESSAGE_ENTITY_NAME)
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int
)