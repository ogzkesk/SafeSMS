package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class QueryContacts @Inject constructor(private val smsRepository: SmsRepository) {

    operator fun invoke(query: String) = smsRepository.queryContacts(query)

}