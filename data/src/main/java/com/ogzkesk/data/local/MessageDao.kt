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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>) : List<Long>

    @Delete
    suspend fun removeMessage(message: MessageEntity)

    @Query("SELECT * from $MESSAGE_TABLE")
    fun fetchMessages() : Flow<List<MessageEntity>>


}