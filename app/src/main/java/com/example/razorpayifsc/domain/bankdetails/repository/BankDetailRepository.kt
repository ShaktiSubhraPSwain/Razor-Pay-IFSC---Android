package com.example.razorpayifsc.domain.bankdetails.repository

import com.example.razorpayifsc.domain.bankdetails.model.NetworkResponse
import com.example.razorpayifsc.domain.bankdetails.model.BankDetailsEntity

interface BankDetailRepository {
    suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error>
}
