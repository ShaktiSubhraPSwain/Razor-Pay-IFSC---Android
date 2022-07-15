package com.example.razorpayifsc.data.repo.analytics

import android.os.Bundle

interface BankAnalytics {
    fun logEvent(name: String, bundle: Bundle)
}
