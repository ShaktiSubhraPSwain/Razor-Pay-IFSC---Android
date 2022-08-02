package com.example.razorpayifsc.utils.callbacks

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkMonitoringUtil(context: Context) : ConnectivityManager.NetworkCallback() {

    private var networkRequest: NetworkRequest
    private var connectivityManager: ConnectivityManager
    private var networkManager: NetworkStateManager? = null

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    init {
        networkManager = NetworkStateManager.getInstance()
        networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Log.d(TAG, "onAvailable() called: Connected to network")
        networkManager?.setConnectivityStatus(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Log.d(TAG, "onLost() called: Lost to network")
        networkManager?.setConnectivityStatus(false)
    }

    fun registerNetworkCallBack() {
        Log.d(TAG, "registerNetworkCallBack() called")
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }
}
