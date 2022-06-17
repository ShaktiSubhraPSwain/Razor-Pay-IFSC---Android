package com.example.razorpayifsc.presentation

import android.app.Application
import com.example.razorpayifsc.BaseApplication
import com.example.razorpayifsc.presentation.callbacks.NetworkMonitoringUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        NetworkMonitoringUtil(applicationContext).registerNetworkCallBack()
    }
}