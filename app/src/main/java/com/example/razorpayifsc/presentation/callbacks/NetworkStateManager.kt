package com.example.razorpayifsc.presentation.callbacks

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkStateManager {

    private var isConnected: Boolean = false
    val isInternetAvailable: Boolean
        get() = isConnected

    companion object {
        private var instance: NetworkStateManager? = null
        private val networkStatusMLD: MutableLiveData<Boolean> = MutableLiveData()

        fun getInstance(): NetworkStateManager? {
            if (instance == null) {
                instance = NetworkStateManager()
            }
            return instance
        }
    }

    fun setConnectivityStatus(status: Boolean) {
        isConnected = status
        if (Looper.myLooper() == Looper.getMainLooper()) {
            networkStatusMLD.value = status
        } else {
            networkStatusMLD.postValue(status)
        }
    }

    fun getConnectivityStatus(): LiveData<Boolean> {
        return networkStatusMLD
    }
}
