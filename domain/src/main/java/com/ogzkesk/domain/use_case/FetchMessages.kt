package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class FetchMessages @Inject constructor(private val repository: SmsRepository) {

    operator fun invoke() = repository.fetchSms()

    fun fetchBySender(sender: String) = repository.fetchSmsBySender(sender)


}