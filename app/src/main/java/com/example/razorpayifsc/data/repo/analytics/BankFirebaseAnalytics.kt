package com.example.razorpayifsc.data.repo.analytics

import android.os.Bundle
import com.example.razorpayifsc.domain.analytics.BankAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class BankFirebaseAnalytics @Inject constructor(private val firebaseAnalytics: FirebaseAnalytics) :
    BankAnalytics {
    override fun logEvent(name: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(name, bundle)
    }
}
