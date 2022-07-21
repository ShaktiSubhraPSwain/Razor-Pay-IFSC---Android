package com.example.razorpayifsc.presentation

import com.example.razorpayifsc.BaseApplication
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(BaseApplication::class)
interface HiltTestApplication
