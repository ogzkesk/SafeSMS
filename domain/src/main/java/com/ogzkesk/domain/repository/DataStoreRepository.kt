package com.ogzkesk.domain.repository

import kotlinx.coroutines.flow.Flow


interface DataStoreRepository {

    suspend fun writePhoneNumber(number: String)
    val readPhoneNumber: Flow<String>


}