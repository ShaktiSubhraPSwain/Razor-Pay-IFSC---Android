package com.example.razorpayifsc.bankDetails.repo

import com.example.razorpayifsc.bankDetails.entity.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.NetworkResponse
import javax.inject.Inject

class BankDetailDataRepository @Inject constructor(
    private val bankDetailApi: BankDetailApi
) : BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error> {
        return bankDetailApi.bankDetails(ifscCode)
    }
}