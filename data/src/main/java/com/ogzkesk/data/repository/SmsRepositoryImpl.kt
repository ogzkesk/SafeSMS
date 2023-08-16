package com.ogzkesk.data.repository

import com.ogzkesk.data.local.MessageDao
import com.ogzkesk.data.mapper.toMessageEntity
import com.ogzkesk.data.mapper.toSmsMessage
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

class SmsRepositoryImpl @Inject constructor(private val smsDao : MessageDao):  SmsRepository {

    override suspend fun insertSms(messages: List<SmsMessage>) {
        withContext(Dispatchers.IO){
            try {
                val entities = messages.map { it.toMessageEntity() }
                entities.forEach { entity ->
                    smsDao.insertMessages(entity)
                }

            }catch (e: IOException){
                Timber.d(e.localizedMessage)
            }
        }
    }

    override suspend fun removeSms(message: SmsMessage) {
        withContext(Dispatchers.IO){
            try {
                smsDao.removeMessage(message.toMessageEntity())
            }catch (e: IOException){
                Timber.d(e.localizedMessage)
            }
        }
    }

    override fun fetchSms(): Flow<Resource<List<SmsMessage>>> {
        return smsDao.fetchMessages()
            .map { entities -> Resource.Success(entities.map { it.toSmsMessage() }) }
            .catch { e -> Resource.Error(e.message,null) }
            .flowOn(Dispatchers.IO)
    }
}