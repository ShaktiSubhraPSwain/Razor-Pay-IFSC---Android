package com.example.razorpayifsc.presentation.bankDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.network.NetworkResponse
import com.example.razorpayifsc.presentation.State
import com.example.razorpayifsc.presentation.base.Resource
import com.example.razorpayifsc.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
        val map = hashMapOf(
            APIConst.ifscCode to ifsc)
        val params = BankDetailUseCase.Params(map)

        // Invoke request to get bank details
        bankDetailUseCase(viewModelScope, params) {
            when (it) {
                is NetworkResponse.Success -> handleSuccess(it.body)
                is NetworkResponse.ApiError -> handleFailure(it.body)
                is NetworkResponse.NetworkError -> handleFailure(it.error)
                is NetworkResponse.UnknownError -> handleFailure(it.error ?: Throwable())
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
    private fun handleFailure(throwable: Throwable) {
        bankAnalytics.logStringEvent(TAG, ifscFailed, throwable.message)
        _bankDetailsLiveEvent.value =
            Resource(throwable = throwable, status = State.ErrorState(throwable))
    }

}