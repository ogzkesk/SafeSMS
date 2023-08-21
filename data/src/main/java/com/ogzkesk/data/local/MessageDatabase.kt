package com.ogzkesk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.data.util.Constants.MESSAGE_DB_NAME
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [MessageEntity::class, ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun dao(): MessageDao


    companion object {
        private var INSTANCE: MessageDatabase? = null

        fun getDatabase(@ApplicationContext context: Context): MessageDatabase {
            return INSTANCE ?: synchronized(MessageDatabase::class.java) {
                Room.databaseBuilder(context, MessageDatabase::class.java, MESSAGE_DB_NAME)
                    .build().also { INSTANCE = it }
            }
        }
    }
}