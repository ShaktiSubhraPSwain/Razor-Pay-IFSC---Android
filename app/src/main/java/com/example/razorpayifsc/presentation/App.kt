package com.example.razorpayifsc.presentation

import android.app.Application
import com.example.razorpayifsc.presentation.callbacks.NetworkMonitoringUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkMonitoringUtil(applicationContext).registerNetworkCallBack()
    }
}