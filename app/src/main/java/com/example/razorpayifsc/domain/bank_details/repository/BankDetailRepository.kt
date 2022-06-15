package com.example.razorpayifsc.domain.bank_details.repository

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.common.NetworkResponse

interface BankDetailRepository {
    suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsResponseEntity, Error>
}