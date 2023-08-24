package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Preferences @Inject constructor(private val dataStore: DataStoreRepository) {

    suspend fun writePhoneNumber(number: String) = dataStore.writePhoneNumber(number)
    val readPhoneNumber: Flow<String> = dataStore.readPhoneNumber



}