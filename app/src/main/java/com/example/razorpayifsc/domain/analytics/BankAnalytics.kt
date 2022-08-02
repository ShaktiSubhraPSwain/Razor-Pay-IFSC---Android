package com.example.razorpayifsc.domain.analytics

import android.os.Bundle

interface BankAnalytics {
    fun logEvent(name: String, bundle: Bundle)
}
