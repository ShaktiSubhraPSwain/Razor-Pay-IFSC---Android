package com.example.razorpayifsc.data.repo

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.NetworkResponse
import javax.inject.Inject

class BankDetailDataRepository @Inject constructor(
    private val bankDetailApi: BankDetailApi
) : BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsResponseEntity, Error> {
        return bankDetailApi.bankDetails(ifscCode)
    }
}