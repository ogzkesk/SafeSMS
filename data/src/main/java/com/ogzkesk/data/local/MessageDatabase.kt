package com.ogzkesk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ogzkesk.data.local.entities.MessageEntity

@Database(entities = [MessageEntity::class], version = 5, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun dao() : MessageDao

}