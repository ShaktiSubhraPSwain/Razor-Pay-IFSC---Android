package com.example.razorpayifsc.data.repo

import com.example.razorpayifsc.data.entity.BankDetailsApiEntity
import com.example.razorpayifsc.domain.bankdetails.model.NetworkResponse
import com.example.razorpayifsc.utils.APIConst
import retrofit2.http.GET
import retrofit2.http.Path

interface BankDetailApi {
    @GET("/{${APIConst.ifscCode}}")
    suspend fun bankDetails(@Path(APIConst.ifscCode) ifscCode: String): NetworkResponse<BankDetailsApiEntity, Error>
}
