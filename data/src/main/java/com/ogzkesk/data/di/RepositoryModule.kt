package com.ogzkesk.data.di

import com.ogzkesk.data.repository.SmsRepositoryImpl
import com.ogzkesk.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindSmsRepository(smsRepositoryImpl: SmsRepositoryImpl) : SmsRepository

}