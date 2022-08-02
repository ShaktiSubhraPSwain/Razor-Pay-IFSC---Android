package com.example.razorpayifsc.utils

import android.os.Bundle
import com.example.razorpayifsc.domain.analytics.BankAnalytics

fun BankAnalytics.logStringEvent(tag: String, key: String, value: String?) {
    this.logEvent(tag, Bundle().apply { putString(key, value) })
}
