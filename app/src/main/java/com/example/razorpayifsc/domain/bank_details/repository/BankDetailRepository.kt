package com.example.razorpayifsc.domain.bank_details.repository

import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.common.network.NetworkResponse

interface BankDetailRepository {
    suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error>
}
