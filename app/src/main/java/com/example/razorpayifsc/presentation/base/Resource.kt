package com.example.razorpayifsc.presentation.base

import com.example.razorpayifsc.presentation.State

data class Resource<out T> constructor(
    val status: State<T>,
    val data: T? = null,
    val throwable: Throwable? = null
)
