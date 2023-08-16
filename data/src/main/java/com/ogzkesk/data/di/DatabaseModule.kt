package com.ogzkesk.data.di

import android.app.Application
import androidx.room.Room
import com.ogzkesk.data.local.MessageDao
import com.ogzkesk.data.local.MessageDatabase
import com.ogzkesk.data.util.Constants.MESSAGE_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application : Application): MessageDatabase {
        return Room.databaseBuilder(
            application,
            MessageDatabase::class.java,
            MESSAGE_DB_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(messageDatabase: MessageDatabase) : MessageDao {
        return messageDatabase.dao()
    }

}