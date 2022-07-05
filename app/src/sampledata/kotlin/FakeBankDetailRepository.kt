package com.example.razorpayifsc

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.NetworkResponse

class FakeBankDetailRepository: BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsResponseEntity, Error> {
        return NetworkResponse.Success(bankDetailResponse())
    }
}