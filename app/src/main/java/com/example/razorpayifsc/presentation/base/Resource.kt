package com.example.razorpayifsc.presentation.base

data class Resource<out T> constructor(
    val status: State<T>,
    val data: T? = null,
    val throwable: Throwable? = null
)
