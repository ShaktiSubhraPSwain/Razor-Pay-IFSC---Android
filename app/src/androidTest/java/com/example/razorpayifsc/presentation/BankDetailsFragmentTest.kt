package com.example.razorpayifsc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.FakeBankDetailRepository
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.presentation.bankDetails.ui.BankDetailsFragment
import com.example.razorpayifsc.presentation.bankDetails.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager
import com.example.razorpayifsc.presentation.utils.launchFragmentInHiltContainer
import com.example.razorpayifsc.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BankDetailsFragmentTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var navController: NavController

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var bankDetailRepository = FakeBankDetailRepository()

    private var bankAnalytics: BankAnalytics = mock(BankAnalytics::class.java)

    private var networkStateManager = NetworkStateManager.getInstance()

    private val bankDetailsUseCase by lazy {
        BankDetailUseCase(bankDetailRepository)
    }

    private lateinit var mockViewModel: BankDetailsViewModel

    @Before
    fun setUp() {
        hitRule.inject()

        networkStateManager?.setConnectivityStatus(true)

        mockViewModel = BankDetailsViewModel(bankDetailsUseCase, bankAnalytics)

        navController = mock(NavController::class.java)
        navController.setViewModelStore(ViewModelStore())
        navController.setGraph(R.navigation.navigation)

        navController.setGraph(R.navigation.navigation)

        launchFragmentInHiltContainer<BankDetailsFragment>(
            initializeViewModel = {
                (this as BankDetailsFragment).viewModel = mockViewModel
            }
        )
    }

    @Test
    fun test_Fragment_Input() {
        onView(withId(R.id.etIfscCode)).check(matches(isDisplayed()))
        // Submit btn is disable in the initial state
        onView(withId(R.id.btnSubmit)).check(matches(isNotEnabled()))

        // After entering the text button should enable
        onView(withId(R.id.etIfscCode)).perform(typeText("1234"))
        onView(withId(R.id.btnSubmit)).check(matches(isEnabled()))
    }

    @Test
    fun test_Input_And_InternetIsOFF() {
        // Internet is off
        networkStateManager?.setConnectivityStatus(false)

        onView(withId(R.id.etIfscCode)).check(matches(isDisplayed()))
        // Submit btn is disable in the initial state
        onView(withId(R.id.btnSubmit)).check(matches(isNotEnabled()))

        // Internet is off & text is not empty -> Button disable
        onView(withId(R.id.etIfscCode)).perform(typeText("1234"))

        onView(withId(R.id.btnSubmit)).check(matches(isNotEnabled()))
    }

    @Test
    fun test_SubmitBtnTap_HappyPath() {
        // Internet is off
        networkStateManager?.setConnectivityStatus(true)

        // Submit btn is disable in the initial state
        onView(withId(R.id.btnSubmit)).check(matches(isNotEnabled()))

        // Internet is off & text is not empty -> Button disable
        onView(withId(R.id.etIfscCode)).perform(typeText("ICIC0000361"))
        onView(withId(R.id.btnSubmit)).check(matches(isEnabled()))
        onView(withId(R.id.btnSubmit)).perform(click())
        onView(withId(R.id.tvBankName)).check(matches(withText("ICICI Bank")))
    }
}
