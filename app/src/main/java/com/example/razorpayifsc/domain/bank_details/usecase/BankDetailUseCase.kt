package com.example.razorpayifsc.domain.bank_details.usecase

import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.BaseCoroutinesUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.utils.APIConst
import javax.inject.Inject

class BankDetailUseCase @Inject constructor(private val bankDetailRepository: BankDetailRepository):
    BaseCoroutinesUseCase<BankDetailsEntity, BankDetailUseCase.Params>() {

    data class Params(val hashMap: HashMap<String, String>)

    override suspend fun run(params: Params): NetworkResponse<BankDetailsEntity, Error> {
        return bankDetailRepository.getBankDetailFromIFSC(params.hashMap[APIConst.ifscCode] ?: "")
    }
}