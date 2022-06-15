package com.example.razorpayifsc.data.repo

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.common.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BankDetailApi {
    @GET("/{ifscCode}")
    suspend fun bankDetails(@Path("ifscCode") ifscCode: String): NetworkResponse<BankDetailsResponseEntity, Error>
}