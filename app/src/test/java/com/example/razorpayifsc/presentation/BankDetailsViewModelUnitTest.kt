package com.example.razorpayifsc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.presentation.bankDetails.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.presentation.base.Resource
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

import java.io.IOException
import com.example.razorpayifsc.*
import io.mockk.*
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.mockito.junit.MockitoRule

class BankDetailsViewModelUnitTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var bankAnalytics: BankAnalytics

    private var bankDetailRepository: BankDetailRepository = mockk()

    private val bankDetailViewModel by lazy {
        BankDetailsViewModel(
            BankDetailUseCase(bankDetailRepository),
            bankAnalytics
        )
    }

    private val bankDetails = bankDetailResponse()
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

            //start observing
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

            //start observing
            bankDetailViewModel.bankDetailsLiveEvent.observeForever(observer)

            //create list to store values
            val list = arrayListOf<Resource<BankDetailsEntity>>()
            observer.captureObserverChanges(list)


            coEvery { bankDetailRepository.getBankDetailFromIFSC(MOCK_IFSC_CODE) } coAnswers {
                NetworkResponse.Success(bankDetailResponse())
            }

            bankDetailViewModel.fetchBankDetails(MOCK_IFSC_CODE)
            assertThat(list.first(), `is`(loadingBankDetailsResponse))
            assertThat(list.last(), `is`(successBankDetailsResponse))
        }
}