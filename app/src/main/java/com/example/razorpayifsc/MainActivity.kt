package com.example.razorpayifsc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.razorpayifsc.databinding.ActivityMainBinding
import com.example.razorpayifsc.presentation.base.SafeObserver
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        initNetworkState()
    }

    private fun initNetworkState() {
        NetworkStateManager.getInstance()?.getConnectivityStatus()?.observe(this,
            SafeObserver(this::handleNetworkState)
        )
    }

    private fun handleNetworkState(status: Boolean) {
        if (!status) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }
}