package com.ogzkesk.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.data.util.Constants.MESSAGE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessages(messages: MessageEntity) : Long


    @Delete
    suspend fun removeMessage(message: MessageEntity)

    @Query("SELECT * from MessageEntity")
    fun fetchMessages() : Flow<List<MessageEntity>>


}