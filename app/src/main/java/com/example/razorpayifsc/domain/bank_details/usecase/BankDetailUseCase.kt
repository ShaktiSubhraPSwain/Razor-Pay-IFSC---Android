package com.example.razorpayifsc.domain.bank_details.usecase

import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BankDetailUseCase @Inject constructor(
    private val bankDetailRepository: BankDetailRepository) {

    suspend operator fun invoke(ifscCode: String) = withContext(Dispatchers.IO) {
        bankDetailRepository.getBankDetailFromIFSC(ifscCode)
    }
}
