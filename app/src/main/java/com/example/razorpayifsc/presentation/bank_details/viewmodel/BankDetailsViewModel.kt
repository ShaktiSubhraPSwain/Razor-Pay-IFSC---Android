package com.example.razorpayifsc.presentation.bank_details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpayifsc.domain.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.bank_details.model.NetworkResponse
import com.example.razorpayifsc.presentation.base.State
import com.example.razorpayifsc.presentation.base.Resource
import com.example.razorpayifsc.utils.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BankDetailsViewModel @Inject constructor(
    private var bankDetailUseCase: BankDetailUseCase,
    private var bankAnalytics: BankAnalytics
) : ViewModel() {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private val _bankDetailsLiveEvent = MutableLiveData<Resource<BankDetailsEntity>>()
    val bankDetailsLiveEvent: LiveData<Resource<BankDetailsEntity>> = _bankDetailsLiveEvent

    /**
     * Enable & disable the button on the basis of networkStatus & test value
     * Returns true if internet is on & test is not empty
     */
    fun buttonEnableStatus(networkStatus: Boolean?, test: CharSequence?) =
        networkStatus.value() && test.value().isNotEmpty()

    /**
     * Fetched the bank details from IFSC code from server
     */
    fun fetchBankDetails(ifscCode: String) {
        fetchBankDetailsFromRemote(ifscCode)
    }

    /**
     * Get the bank details from server & set the value to live data to observe
     */
    private fun fetchBankDetailsFromRemote(ifsc: String) {
        _bankDetailsLiveEvent.value = Resource(State.LoadingState)

        viewModelScope.launch {
            when (val result = bankDetailUseCase(ifsc)) {
                is NetworkResponse.Success -> handleSuccess(result.body)
                is NetworkResponse.ApiError -> handleFailure(result.body)
                is NetworkResponse.NetworkError -> handleFailure(result.error)
                is NetworkResponse.UnknownError -> handleFailure(result.error)
            }
        }
    }

    /**
     * Handled success response of ifsc code request
     * dispatched the success response to live data to observe
     */
    private fun handleSuccess(data: BankDetailsEntity) {
        bankAnalytics.logStringEvent(TAG, ifscSuccess, data.ifsc)
        _bankDetailsLiveEvent.value =
            Resource(data = data, status = State.DataState(data))
    }

    /**
     * Handled failure response of ifsc code request
     * dispatched the error response to live data to observe
     */
    private fun handleFailure(throwable: Throwable?) {
        throwable?.let {
            bankAnalytics.logStringEvent(TAG, ifscFailed, it.message)
            _bankDetailsLiveEvent.value =
                Resource(throwable = it, status = State.ErrorState(it))
        }
    }
}
