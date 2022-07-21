package com.example.razorpayifsc.domain.analytics

import android.os.Bundle
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.google.firebase.analytics.FirebaseAnalytics

class BankFirebaseAnalytics(private val firebaseAnalytics: FirebaseAnalytics) : BankAnalytics {
    override fun logEvent(name: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(name, bundle)
    }
}
