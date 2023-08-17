package com.ogzkesk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity

@Database(entities = [MessageEntity::class,ContactEntity::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun dao() : MessageDao

}