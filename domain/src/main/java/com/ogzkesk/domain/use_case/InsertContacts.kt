package com.ogzkesk.domain.use_case

import com.ogzkesk.domain.model.Contact
import com.ogzkesk.domain.repository.SmsRepository
import javax.inject.Inject

class InsertContacts @Inject constructor(private val smsRepository: SmsRepository){

    suspend operator fun invoke(contacts: List<Contact>) {
        smsRepository.insertContacts(contacts)
    }
}