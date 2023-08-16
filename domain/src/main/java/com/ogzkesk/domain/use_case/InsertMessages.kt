package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject
import javax.inject.Singleton


class InsertMessages @Inject constructor(private val repository: SmsRepository) {

    suspend operator fun invoke(messages: List<SmsMessage>) {
        repository.insertSms(messages)
    }

}