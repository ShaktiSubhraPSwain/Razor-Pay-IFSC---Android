package com.example.razorpayifsc

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.razorpayifsc.presentation.HiltTestApplication

class TestAppJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
