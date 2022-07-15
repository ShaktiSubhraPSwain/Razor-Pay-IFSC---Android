package com.example.razorpayifsc.domain

import com.example.razorpayifsc.bankDetailResponse
import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.network.NetworkResponse

class FakeBankDetailRepository : BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsResponseEntity, Error> {
        return NetworkResponse.Success(bankDetailResponse())
    }
}
