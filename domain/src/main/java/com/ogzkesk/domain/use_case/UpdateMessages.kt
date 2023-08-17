package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.model.SmsMessage
import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class UpdateMessages @Inject constructor(private val smsRepository: SmsRepository){

    suspend operator fun invoke(messages: List<SmsMessage>) {
        smsRepository.updateSms(messages)
    }

}