package com.ogzkesk.domain.repository

import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface SmsRepository {

    suspend fun insertSms(messages: List<SmsMessage>)

    suspend fun insertContacts(contacts: List<Contact>)

    suspend fun removeSms(message: SmsMessage)

    suspend fun updateSms(messages: List<SmsMessage>)

    fun fetchSms() : Flow<Resource<List<SmsMessage>>>

    fun fetchContacts() : Flow<Resource<List<Contact>>>

    fun querySms(query: String) : Flow<Resource<List<SmsMessage>>>

    fun queryContacts(query: String) : Flow<Resource<List<Contact>>>

    fun fetchSmsByThreadId(threadId: Int):  Flow<Resource<List<SmsMessage>>>


}