package com.example.razorpayifsc.mock

import com.example.razorpayifsc.domain.analytics.BankAnalytics
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk

/**
 * Mock FirebaseAnalytics
 */
fun mockBankAnalytics(): BankAnalytics {
    val bankAnalytics = mockk<BankAnalytics>()
    every { bankAnalytics.logEvent(any(), any()) } just Runs
    return bankAnalytics
}
