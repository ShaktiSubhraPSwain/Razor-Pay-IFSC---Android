package com.example.razorpayifsc.presentation.bankDetails.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.data.mapper.BankDetailsMapper.toDomain
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.presentation.State
import com.example.razorpayifsc.presentation.base.Resource
import com.example.razorpayifsc.presentation.base.SingleLiveEvent
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BankdetailsViewModel @Inject constructor(
    private var bankDetailUseCase: BankDetailUseCase,
    private var bankAnalytics: BankAnalytics
) : ViewModel() {

    companion object {
        val TAG: String = javaClass.simpleName
    }
    private val _bankDetailsLiveEvent = SingleLiveEvent<Resource<BankDetailsEntity>>()
    val bankDetailsLiveEvent: SingleLiveEvent<Resource<BankDetailsEntity>> = _bankDetailsLiveEvent

    fun fetchBankDetails(ifscCode: String) {
        fetchBankDetailsFromRemote(ifscCode)
    }

    private fun fetchBankDetailsFromRemote(ifscCode: String) {
        _bankDetailsLiveEvent.value = Resource(State.LoadingState)
        try {
            val map = HashMap<String, String>()
            map["ifscCode"] = ifscCode
            val params = BankDetailUseCase.Params(map)
            bankDetailUseCase.invoke(viewModelScope, params) {
                when (it) {
                    is NetworkResponse.Success -> handleSuccess(it.body)
                    is NetworkResponse.ApiError -> handleFailure(it.body)
                    is NetworkResponse.NetworkError -> handleFailure(it.error)
                    is NetworkResponse.UnknownError -> handleFailure(it.error ?: Throwable())
                }
            }
        } catch (e: Exception) { _bankDetailsLiveEvent.value = Resource(throwable = e, status = State.ErrorState(e)) }
    }

    private fun handleSuccess(data: BankDetailsResponseEntity)  {

        bankAnalytics.logEvent(TAG, Bundle().apply {putString("ifsc_success", data.ifsc)})

        val bankMapper = data.toDomain()
        _bankDetailsLiveEvent.value = Resource(data = bankMapper, status = State.DataState(bankMapper))
    }

    private fun handleFailure(throwable: Throwable) {
        bankAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, Bundle().apply {putString("ifsc_failed", throwable.message)})
        _bankDetailsLiveEvent.value = Resource(throwable = throwable, status = State.ErrorState(throwable))
    }

}