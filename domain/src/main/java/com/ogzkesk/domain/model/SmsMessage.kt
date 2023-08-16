package com.ogzkesk.domain.model


data class SmsMessage(
    val isSpam : Boolean,
    val isFav : Boolean,
    val message: String,
    val sender: String,
    val date: Long,
    val thread: Int,
    val id: Long
)