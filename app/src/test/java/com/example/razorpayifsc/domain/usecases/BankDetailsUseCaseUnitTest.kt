package com.example.razorpayifsc.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.razorpayifsc.MOCK_IFSC_CODE
import com.example.razorpayifsc.MainCoroutineRule
import com.example.razorpayifsc.NOT_FOUND
import com.example.razorpayifsc.data.mapper.toBankDetailsEntity
import com.example.razorpayifsc.domain.bankdetails.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bankdetails.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.bankdetails.model.NetworkResponse
import com.example.razorpayifsc.mock.buildBankDetailResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.* // ktlint-disable no-wildcard-imports
import java.io.IOException

class BankDetailsUseCaseUnitTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule(testDispatcher)

    private var bankDetailRepository = mockk<BankDetailRepository>()

    @ExperimentalCoroutinesApi
    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository, testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given getBankDetailFromIFSC() returned success response, when IFSC code is correct`() =
        runTest {
            coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
                NetworkResponse.Success(buildBankDetailResponse().toBankDetailsEntity())
            }

            bankDetailsUseCase(MOCK_IFSC_CODE).let {
                when (it) {
                    is NetworkResponse.Success -> Assert.assertEquals(
                        it.body.ifsc, MOCK_IFSC_CODE
                    )
                    else -> assert(false)
                }
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given getBankDetailFromIFSC() returned IOException(NOT_FOUND), when IFSC code is wrong`() =
        runTest {
            coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
                NetworkResponse.NetworkError(IOException(NOT_FOUND))
            }

            bankDetailsUseCase(MOCK_IFSC_CODE).let {
                when (it) {
                    is NetworkResponse.NetworkError -> Assert.assertEquals(
                        it.error.message, NOT_FOUND
                    )
                    else -> assert(false)
                }
            }
        }
}
