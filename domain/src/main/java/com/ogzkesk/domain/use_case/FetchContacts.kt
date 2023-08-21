package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class FetchContacts @Inject constructor(private val smsRepository: SmsRepository) {

    operator fun invoke() = smsRepository.fetchContacts()
}