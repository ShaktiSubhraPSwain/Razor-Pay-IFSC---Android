package com.example.razorpayifsc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.razorpayifsc.MainCoroutineRule
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.presentation.bankDetails.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.presentation.base.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

import org.mockito.stubbing.Answer

import org.mockito.Mockito.`when`
import java.io.IOException
import android.os.Bundle
import com.example.razorpayifsc.MOCK_IFSC_CODE
import com.example.razorpayifsc.bankDetailResponse
import com.example.razorpayifsc.hashMap
import org.mockito.junit.MockitoRule

class BankDetailsViewModelUnitTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @Mock
    lateinit var bankDetailsObserver: Observer<Resource<BankDetailsEntity>>

    @Mock
    lateinit var bankAnalytics: BankAnalytics

    @Captor
    private val argumentCaptor: ArgumentCaptor<Resource<BankDetailsEntity>>? = null

    private var bankDetailRepository = mock<BankDetailRepository>()

    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository)
    }

    private val bankDetailViewModel by lazy { BankDetailsViewModel(bankDetailsUseCase, bankAnalytics) }

    private val bankDetailsMapper = bankDetailResponse().toDomain()
    private val successBankDetailsResponse: Resource<BankDetailsEntity> =
        Resource(status = State.DataState(bankDetailsMapper), data = bankDetailsMapper)
    private val errorBankDetailsResponse: Resource<Throwable> =
        Resource(
            throwable = IOException("Not found"),
            status = State.ErrorState(IOException("Not found"))
        )
    private val loadingBankDetailsResponse: Resource<BankDetailsEntity> =
        Resource(State.LoadingState)

    @Mock
    lateinit var extras: Bundle

    @Before
    fun setUp() {
        Mockito.doNothing().`when`(extras).putString(anyOrNull(), anyOrNull())
        `when`(bankAnalytics.logEvent(anyOrNull(), anyOrNull())).thenAnswer {}
    }
    @ExperimentalCoroutinesApi
    @Test
    fun fetchBankDetail_Success() {
        val map = BankDetailUseCase.Params(hashMap)

        coroutineScope.runBlockingTest {
            `when`(bankDetailsUseCase.run(map)).thenAnswer(Answer {
                Thread.sleep(1000)
                return@Answer NetworkResponse.Success(bankDetailResponse())
            })
        }

        bankDetailViewModel.bankDetailsLiveEvent.observeForever(bankDetailsObserver)
        bankDetailViewModel.fetchBankDetails(MOCK_IFSC_CODE)

        argumentCaptor?.apply {
            Mockito.verify(bankDetailsObserver, Mockito.times(1)).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(loadingBankDetailsResponse))
            )
        }

        Thread.sleep(1000)

        argumentCaptor?.apply {
            Mockito.verify(bankDetailsObserver, Mockito.times(2)).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(successBankDetailsResponse))
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchBankDetail_Error() {
        val map = BankDetailUseCase.Params(hashMap)

        coroutineScope.runBlockingTest {
            `when`(bankDetailsUseCase.run(map)).thenAnswer(Answer {
                Thread.sleep(1000)
                return@Answer NetworkResponse.NetworkError(IOException("Not found"))
            })
        }

        bankDetailViewModel.bankDetailsLiveEvent.observeForever(bankDetailsObserver)
        bankDetailViewModel.fetchBankDetails(MOCK_IFSC_CODE)

        argumentCaptor?.apply {
            Mockito.verify(bankDetailsObserver, Mockito.times(1)).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(loadingBankDetailsResponse))
            )
        }

        Thread.sleep(1000)

        argumentCaptor?.apply {
            Mockito.verify(bankDetailsObserver, Mockito.times(2)).onChanged(capture())
            MatcherAssert.assertThat(
                Gson().toJson(value),
                CoreMatchers.`is`(Gson().toJson(errorBankDetailsResponse))
            )
        }
    }
}