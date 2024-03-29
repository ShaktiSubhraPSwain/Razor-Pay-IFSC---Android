package com.example.razorpayifsc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.razorpayifsc.presentation.bankdetails.ui.BankDetailsFragment
import com.example.razorpayifsc.utils.callbacks.NetworkStateManager
import com.example.razorpayifsc.presentation.utils.launchFragmentInHiltContainer
import com.example.razorpayifsc.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BankDetailsFragmentTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var networkStateManager = NetworkStateManager.getInstance()

    @Before
    fun setUp() {
        hitRule.inject()
        networkStateManager?.setConnectivityStatus(true)
        launchFragmentInHiltContainer<BankDetailsFragment>()
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
