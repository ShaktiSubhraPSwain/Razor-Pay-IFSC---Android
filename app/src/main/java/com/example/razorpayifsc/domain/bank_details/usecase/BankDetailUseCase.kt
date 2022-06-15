package com.example.razorpayifsc.domain.bank_details.usecase

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.BaseCoroutinesUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import javax.inject.Inject

class BankDetailUseCase @Inject constructor(private val bankDetailRepository: BankDetailRepository):
    BaseCoroutinesUseCase<BankDetailsResponseEntity, BankDetailUseCase.Params>() {

    data class Params(val hashMap: HashMap<String, String>)

    override suspend fun run(params: Params): NetworkResponse<BankDetailsResponseEntity, Error> {
        return bankDetailRepository.getBankDetailFromIFSC(params.hashMap["ifscCode"] ?: "")
    }
}