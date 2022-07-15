package com.example.razorpayifsc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.razorpayifsc.presentation.base.SafeObserver
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager
import com.example.razorpayifsc.utils.showSnackbar
import com.example.razorpayifsc.utils.value
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import android.util.TypedValue
import com.example.razorpayifsc.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNetworkState()
        supportFragmentManager.beginTransaction()
    }

    /**
     * Initialize network state and set listener to network connectivity status
     * on any update on connectivity getConnectivityStatus will notified
     */
    private fun initNetworkState() {
        NetworkStateManager.getInstance()?.getConnectivityStatus()?.observe(
            this,
            SafeObserver(
                this::handleNetworkState
            )
        )
    }

    /**
     * Handling network state. Show snackbar on internet status
     */
    private fun handleNetworkState(status: Boolean) {
        // Dismiss the snackbar if it is shown
        if (snackbar?.isShown.value()) {
            snackbar?.dismiss()
        }
        snackbar = if (status) {
            val outValue = TypedValue()
            this.theme.resolveAttribute(R.attr.colorPrimary, outValue, true)
            binding.root.showSnackbar(R.string.back_online, outValue.data)
        } else {
            // Show the Snackbar indefinitely on no internet
            binding.root.showSnackbar(
                R.string.no_internet_connection,
                ContextCompat.getColor(
                    applicationContext,
                    R.color.red
                ),
                Snackbar.LENGTH_INDEFINITE
            )
        }
    }
}
