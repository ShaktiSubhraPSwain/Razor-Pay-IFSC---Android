package com.example.razorpayifsc.domain.bank_details.repository

import com.example.razorpayifsc.bankDetails.entity.BankDetailsEntity
import com.example.razorpayifsc.domain.common.NetworkResponse

interface BankDetailRepository {
    suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error>
}