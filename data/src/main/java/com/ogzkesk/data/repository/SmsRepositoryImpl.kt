package com.ogzkesk.data.repository

import com.ogzkesk.data.local.MessageDao
import com.ogzkesk.data.mapper.toContactEntity
import com.ogzkesk.data.mapper.toMessageEntity
import com.ogzkesk.data.util.wrapContact
import com.ogzkesk.data.util.wrapMessage
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.repository.SmsRepository
import com.ogzkesk.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
        return smsDao.fetchMessages().wrapMessage()
    }


    override fun querySms(query: String): Flow<Resource<List<SmsMessage>>> {
        return smsDao.queryMessages(query).wrapMessage()
    }

    override fun fetchSmsByThreadId(threadId: Int): Flow<Resource<List<SmsMessage>>> {
        return smsDao.fetchByThreadId(threadId).wrapMessage()
    }

    override fun fetchContacts(): Flow<Resource<List<Contact>>> {
        return smsDao.fetchContacts().wrapContact()
    }


    override fun queryContacts(query: String): Flow<Resource<List<Contact>>> {
        return smsDao.queryContacts(query).wrapContact()
    }
}



