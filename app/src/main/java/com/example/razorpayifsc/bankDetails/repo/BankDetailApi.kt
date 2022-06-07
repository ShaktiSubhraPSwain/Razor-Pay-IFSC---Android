package com.example.razorpayifsc.bankDetails.repo

import com.example.razorpayifsc.bankDetails.entity.BankDetailsEntity
import com.example.razorpayifsc.domain.common.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BankDetailApi {
    @GET("/{ifscCode}")
    suspend fun bankDetails(@Path("ifscCode") ifscCode: String): NetworkResponse<BankDetailsEntity, Error>
}