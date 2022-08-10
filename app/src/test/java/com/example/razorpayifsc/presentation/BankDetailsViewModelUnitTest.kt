package com.example.razorpayifsc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.razorpayifsc.domain.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bankdetails.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bankdetails.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bankdetails.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.bankdetails.model.NetworkResponse
import com.example.razorpayifsc.presentation.bankdetails.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.presentation.base.Resource
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import java.io.IOException
/* ktlint-disable no-wildcard-imports */
import com.example.razorpayifsc.*
import com.example.razorpayifsc.data.mapper.toBankDetailsEntity
import com.example.razorpayifsc.mock.buildBankDetailResponse
import com.example.razorpayifsc.mock.mockBankAnalytics
import com.example.razorpayifsc.mock.mockBundleOf
import com.example.razorpayifsc.presentation.base.State
import io.mockk.*
import kotlinx.coroutines.test.*
/* ktlint-enable no-wildcard-imports */
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.mockito.junit.MockitoRule

class BankDetailsViewModelUnitTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule(testDispatcher)

    private lateinit var bankAnalytics: BankAnalytics

    private val bankDetailRepository: BankDetailRepository = mockk()

    @ExperimentalCoroutinesApi
    private val bankDetailViewModel by lazy {
        BankDetailsViewModel(
            BankDetailUseCase(bankDetailRepository, testDispatcher),
            bankAnalytics
        )
    }

    private val bankDetails = buildBankDetailResponse().toBankDetailsEntity()
    private val successBankDetailsResponse: Resource<BankDetailsEntity> =
        Resource(status = State.DataState(bankDetails), data = bankDetails)

    private val errorBankDetailsResponse: Resource<Throwable> =
        Resource(
            throwable = IOException(NOT_FOUND),
            status = State.ErrorState(IOException(NOT_FOUND))
        )
    private val loadingBankDetailsResponse: Resource<BankDetailsEntity> =
        Resource(State.LoadingState)

    @Before
    fun setUp() {
        mockBundleOf()
        bankAnalytics = mockBankAnalytics()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given DataResult Error returned from useCase, should result loading & error`() =
        runTest {
            val observer = mockk<Observer<Resource<BankDetailsEntity>>>()

            coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
                NetworkResponse.NetworkError(IOException(NOT_FOUND))
            }

            // start observing
            bankDetailViewModel.bankDetailsLiveEvent.observeForever(observer)

            val list = arrayListOf<Resource<BankDetailsEntity>>()

            observer.captureObserverChanges(list)

            bankDetailViewModel.fetchBankDetails(MOCK_IFSC_CODE)

            assertThat(list.first(), `is`(loadingBankDetailsResponse))
            assertThat(
                Gson().toJson(list.last()),
                CoreMatchers.`is`(Gson().toJson(errorBankDetailsResponse))
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `Given DataResult Success returned from useCase, should result loading & success`() =
        runTest {
            val observer = mockk<Observer<Resource<BankDetailsEntity>>>()

            // start observing
            bankDetailViewModel.bankDetailsLiveEvent.observeForever(observer)

            // create list to store values
            val list = arrayListOf<Resource<BankDetailsEntity>>()
            observer.captureObserverChanges(list)

            coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
                NetworkResponse.Success(buildBankDetailResponse().toBankDetailsEntity())
            }
            bankDetailViewModel.fetchBankDetails(MOCK_IFSC_CODE)
            assertThat(list.first(), `is`(loadingBankDetailsResponse))
            assertThat(list.last(), `is`(successBankDetailsResponse))
        }
}
