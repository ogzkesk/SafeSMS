package com.ogzkesk.data.di

import com.ogzkesk.data.repository.DatastoreRepositoryImpl
import com.ogzkesk.data.repository.SmsRepositoryImpl
import com.ogzkesk.domain.repository.DataStoreRepository
import com.ogzkesk.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindSmsRepository(smsRepositoryImpl: SmsRepositoryImpl) : SmsRepository

    @Binds
    @Singleton
    fun bindDatastore(
        datastoreRepositoryImpl: DatastoreRepositoryImpl
    ) : DataStoreRepository



}