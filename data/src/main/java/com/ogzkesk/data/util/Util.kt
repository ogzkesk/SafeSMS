package com.ogzkesk.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.ogzkesk.core.ext.flowOnIO
import com.ogzkesk.data.local.entities.ContactEntity
import com.ogzkesk.data.local.entities.MessageEntity
import com.ogzkesk.data.mapper.toContact
import com.ogzkesk.data.mapper.toSmsMessage
import com.ogzkesk.data.util.Constants.DATA_STORE
import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun Flow<List<MessageEntity>>.wrapMessage() : Flow<Resource<List<SmsMessage>>> {
    return map { entities -> Resource.Success(entities.map { it.toSmsMessage() }) }
        .catch { e -> Resource.Error(e.message,null) }
        .flowOnIO()
}

fun Flow<List<ContactEntity>>.wrapContact() : Flow<Resource<List<Contact>>> {
    return map { entities -> Resource.Success(entities.map { it.toContact() }) }
        .catch { e -> Resource.Error(e.message,null) }
        .flowOnIO()
}

val Context.dataStore by preferencesDataStore(DATA_STORE)