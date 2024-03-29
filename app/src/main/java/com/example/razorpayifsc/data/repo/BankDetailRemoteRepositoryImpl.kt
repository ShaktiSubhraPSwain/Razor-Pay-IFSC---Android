package com.example.razorpayifsc.data.repo

import com.example.razorpayifsc.data.mapper.toBankDetailsEntity
import com.example.razorpayifsc.domain.bankdetails.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bankdetails.repository.BankDetailRepository
import com.example.razorpayifsc.domain.bankdetails.model.NetworkResponse
import javax.inject.Inject

class BankDetailRemoteRepositoryImpl @Inject constructor(
    private val bankDetailApi: BankDetailApi
) : BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error> {
        return when (val result = bankDetailApi.bankDetails(ifscCode)) {
            is NetworkResponse.Success -> {
                // Mapping response with object
                val mapping = result.body.toBankDetailsEntity()
                NetworkResponse.Success(mapping)
            }
            is NetworkResponse.ApiError -> {
                NetworkResponse.ApiError(result.body, result.code)
            }
            is NetworkResponse.NetworkError -> {
                NetworkResponse.NetworkError(result.error)
            }
            is NetworkResponse.UnknownError -> {
                NetworkResponse.UnknownError(result.error)
            }
        }
    }
}
