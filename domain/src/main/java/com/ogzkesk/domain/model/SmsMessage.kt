package com.ogzkesk.domain.model


data class SmsMessage(
    val isSpam : Boolean,
    val isFav : Boolean,
    val isRead: Boolean,
    val type: String,
    val message: String,
    val sender: String,
    val name: String,
    val date: Long,
    val id: Long
){
    companion object{
        const val SENT = "sent"
        const val RECEIVED = "received"
    }

}