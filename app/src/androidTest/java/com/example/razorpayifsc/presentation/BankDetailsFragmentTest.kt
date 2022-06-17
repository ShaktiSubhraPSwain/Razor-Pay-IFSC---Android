package com.example.razorpayifsc.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.razorpayifsc.presentation.bankDetails.ui.BankDetailsFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.razorpayifsc.R
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.test.espresso.action.ViewActions.typeText
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.presentation.bankDetails.viewmodel.BankdetailsViewModel
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager
import com.example.razorpayifsc.presentation.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BankDetailsFragmentTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    private lateinit var navController: NavController

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var bankDetailRepository = mock(BankDetailRepository::class.java)

    @Mock
    private var bankAnalytics: BankAnalytics = mock(BankAnalytics::class.java)


    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository)
    }

    private lateinit var mockViewModel: BankdetailsViewModel

    @Before
    fun setUp() {
        hitRule.inject()
        NetworkStateManager.getInstance()!!.setConnectivityStatus(true)

        mockViewModel = BankdetailsViewModel(bankDetailsUseCase, bankAnalytics)

        navController = mock(NavController::class.java)
        navController.setViewModelStore(ViewModelStore())
        navController.setGraph(R.navigation.navigation)

        navController.setGraph(R.navigation.navigation)

        launchFragmentInHiltContainer<BankDetailsFragment>(
            initializeViewModel = {
                (this as BankDetailsFragment).viewModel = mockViewModel
            })
    }

    @Test
    fun test_Fragment_Input() {
        onView(withId(R.id.ifscCodeEditText)).check(matches(isDisplayed()))
        /// Submit btn is disable in the initial state
        onView(withId(R.id.submitBtn)).check(matches(isNotEnabled()))

        /// After entering the text button should enable
        onView(withId(R.id.ifscCodeEditText)).perform(typeText("1234"))
        onView(withId(R.id.submitBtn)).check(matches(isEnabled()))
    }

}