package com.example.razorpayifsc

import com.example.razorpayifsc.utils.callbacks.NetworkMonitoringUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        NetworkMonitoringUtil(applicationContext).registerNetworkCallBack()
    }
}
