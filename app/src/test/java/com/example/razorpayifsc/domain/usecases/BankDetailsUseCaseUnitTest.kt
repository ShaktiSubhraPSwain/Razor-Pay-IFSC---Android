package com.example.razorpayifsc.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.razorpayifsc.MOCK_IFSC_CODE
import com.example.razorpayifsc.NOT_FOUND
import com.example.razorpayifsc.bankDetailResponse
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.network.NetworkResponse
import com.example.razorpayifsc.hashMap
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import java.io.IOException

class BankDetailsUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private var bankDetailRepository = mockk<BankDetailRepository>()

    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given getBankDetailFromIFSC() returned success response, when IFSC code is correct`() = runTest {
        val map = BankDetailUseCase.Params(hashMap)

        coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
            NetworkResponse.Success(bankDetailResponse())
        }

        bankDetailsUseCase.run(map).let {
            when (it) {
                is NetworkResponse.Success -> Assert.assertEquals(
                    it.body.ifsc, MOCK_IFSC_CODE
                )
                else -> {}
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given getBankDetailFromIFSC() returned IOException(NOT_FOUND), when IFSC code is wrong`() = runTest {
        val map = BankDetailUseCase.Params(hashMap)
        coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
            NetworkResponse.NetworkError(IOException(NOT_FOUND))
        }

        bankDetailsUseCase.run(map).let {
            when (it) {
                is NetworkResponse.NetworkError -> Assert.assertEquals(
                    it.error.message, NOT_FOUND
                )
                else -> {}
            }
        }
    }

}