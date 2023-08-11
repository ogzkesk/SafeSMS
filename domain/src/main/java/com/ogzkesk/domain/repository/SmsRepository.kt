package com.ogzkesk.domain.repository

import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface SmsRepository {

    suspend fun insertSms(messages: List<SmsMessage>)

    suspend fun removeSms(message: SmsMessage)

    fun fetchSms() : Flow<Resource<List<SmsMessage>>>


}