package com.ogzkesk.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<ContactEntity>): List<Long>

    @Update
    suspend fun updateMessages(messages: List<MessageEntity>): Int

    @Delete
    suspend fun removeMessage(message: MessageEntity)

    @Query("SELECT * from MessageEntity")
    fun fetchMessages(): Flow<List<MessageEntity>>

    @Query("SELECT * from ContactEntity")
    fun fetchContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * from ContactEntity WHERE name LIKE '%' || :query || '%' OR number LIKE '%' || :query || '%'")
    fun queryContacts(query: String): Flow<List<ContactEntity>>

    @Query(
        "SELECT * from MessageEntity WHERE " +
                "message LIKE '%' || :query || '%' OR " +
                "sender LIKE '%' || :query || '%' OR " +
                "name LIKE '%' || :query || '%'"
    )
    fun queryMessages(query: String): Flow<List<MessageEntity>>


    @Query("SELECT * from MessageEntity WHERE sender = :sender")
    fun fetchBySender(sender: String): Flow<List<MessageEntity>>

}