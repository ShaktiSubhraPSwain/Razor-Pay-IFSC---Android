package com.example.razorpayifsc.domain.bankdetails.usecase

import com.example.razorpayifsc.di.IODispatcher
import com.example.razorpayifsc.domain.bankdetails.repository.BankDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BankDetailUseCase @Inject constructor(
    private val bankDetailRepository: BankDetailRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(ifscCode: String) = withContext(ioDispatcher) {
        bankDetailRepository.getBankDetailFromIFSC(ifscCode)
    }
}
