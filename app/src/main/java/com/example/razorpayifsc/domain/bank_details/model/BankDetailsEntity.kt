package com.example.razorpayifsc.domain.bank_details.model

data class BankDetailsEntity(
    val bank: String = "",
    val ifsc: String = "",
    val branch: String = "",
    val address: String = "",
    val contact: String?,
    val city: String = "",
    val district: String = "",
    val state: String = "",
    val bankCode: String = "",
)
