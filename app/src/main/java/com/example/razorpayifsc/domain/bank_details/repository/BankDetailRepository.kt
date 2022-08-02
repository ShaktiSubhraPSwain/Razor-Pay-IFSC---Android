package com.example.razorpayifsc.domain.bank_details.repository

import com.example.razorpayifsc.data.network.NetworkResponse
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity

interface BankDetailRepository {
    suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error>
}
