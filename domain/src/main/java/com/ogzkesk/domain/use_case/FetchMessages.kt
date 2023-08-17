package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject
import javax.inject.Singleton

class FetchMessages @Inject constructor(private val repository: SmsRepository) {
    operator fun invoke() = repository.fetchSms()

    fun fetchByThreadId(threadId: Int) = repository.fetchSmsByThreadId(threadId)


}