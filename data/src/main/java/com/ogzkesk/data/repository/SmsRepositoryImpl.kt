package com.ogzkesk.data.repository

import com.ogzkesk.data.local.MessageDao
import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.data.mapper.toContact
import com.ogzkesk.data.mapper.toContactEntity
import com.ogzkesk.data.mapper.toMessageEntity
import com.ogzkesk.data.mapper.toSmsMessage
import com.ogzkesk.data.util.wrap
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.repository.SmsRepository
import com.ogzkesk.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(private val smsDao: MessageDao) : SmsRepository {

    override suspend fun insertSms(messages: List<SmsMessage>) {
        withContext(Dispatchers.IO) {
            try {
                val entities = messages.map { it.toMessageEntity() }
                smsDao.insertMessages(entities)

            } catch (e: IOException) {
                Timber.e(e.localizedMessage)
            }
        }
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        withContext(Dispatchers.IO){
            try {
                val entities = contacts.map { it.toContactEntity() }
                smsDao.insertContacts(entities)
            }catch (e: IOException){
                Timber.e(e.localizedMessage)
            }
        }
    }


    override suspend fun removeSms(message: SmsMessage) {
        withContext(Dispatchers.IO) {
            try {
                smsDao.removeMessage(message.toMessageEntity())
            } catch (e: IOException) {
                Timber.e(e.localizedMessage)
            }
        }
    }


    override suspend fun updateSms(messages: List<SmsMessage>) {
        withContext(Dispatchers.IO){
            try {
                val entities = messages.map { it.toMessageEntity() }
                smsDao.updateMessages(entities)
            }catch (e: Exception){
                Timber.e(e.localizedMessage)
            }
        }
    }


    override fun fetchSms(): Flow<Resource<List<SmsMessage>>> {
        return smsDao.fetchMessages().wrap()
    }

    override fun fetchContacts(): Flow<Resource<List<Contact>>> {
        return smsDao.fetchContacts().wrap()
    }


    override fun queryContacts(query: String): Flow<Resource<List<Contact>>> {
        return smsDao.queryContacts(query).wrap()
    }

    override fun querySms(query: String): Flow<Resource<List<SmsMessage>>> {
        return smsDao.queryMessages(query).wrap()
    }

    override fun fetchSmsByThreadId(threadId: Int): Flow<Resource<List<SmsMessage>>> {
        return smsDao.fetchByThreadId(threadId).wrap()
    }
}



