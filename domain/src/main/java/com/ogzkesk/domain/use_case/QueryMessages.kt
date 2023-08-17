package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class QueryMessages @Inject constructor(private val repository: SmsRepository) {

    operator fun invoke(query: String) = repository.querySms(query)
}