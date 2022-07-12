package com.example.razorpayifsc.data.repo

import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import com.example.razorpayifsc.domain.common.NetworkResponse
import javax.inject.Inject

class BankDetailDataRepository @Inject constructor(
    private val bankDetailApi: BankDetailApi
) : BankDetailRepository {
    override suspend fun getBankDetailFromIFSC(ifscCode: String): NetworkResponse<BankDetailsEntity, Error> {
        return when (val result = bankDetailApi.bankDetails(ifscCode)) {
            is NetworkResponse.Success -> {
                // Mapping response with object
                val mapping = result.body.toDomain()
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