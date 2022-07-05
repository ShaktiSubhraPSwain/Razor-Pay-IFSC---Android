package com.example.razorpayifsc.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.razorpayifsc.MOCK_IFSC_CODE
import com.example.razorpayifsc.bankDetailResponse
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.hashMap
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class BankDetailsUseCaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private var bankDetailRepository = mock<BankDetailRepository>()

    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository)
    }

    @Before
    fun setUp() {
        reset(bankDetailRepository)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testLoginUseCase_success() {
        runBlocking {
            val map = BankDetailUseCase.Params(hashMap)
            whenever(bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE)).thenReturn(
                NetworkResponse.Success(bankDetailResponse())
            )

            bankDetailsUseCase.run(map).let {
                when (it) {
                    is NetworkResponse.Success -> Assert.assertEquals(
                        it.body.ifsc, MOCK_IFSC_CODE
                    )
                    else -> ""
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLoginUseCase_failure() {
        runBlocking {
            val map = BankDetailUseCase.Params(hashMap)

            whenever(bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE)).thenReturn(
                NetworkResponse.NetworkError(IOException("Not found"))
            )

            bankDetailsUseCase.run(map).let {
                when (it) {
                    is NetworkResponse.NetworkError -> Assert.assertEquals(
                        it.error.message, "Not found"
                    )
                    else -> ""
                }
            }
        }
    }
}