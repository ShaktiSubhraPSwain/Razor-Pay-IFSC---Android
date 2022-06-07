package com.example.razorpayifsc.presentation.bankDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpayifsc.bankDetails.entity.BankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.usecase.BankDetailUseCase
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.presentation.State
import com.example.razorpayifsc.presentation.base.Resource
import com.example.razorpayifsc.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BankdetailsViewModel @Inject constructor(
    private var bankDetailUseCase: BankDetailUseCase
) : ViewModel() {

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
                    is NetworkResponse.ApiError -> handleFailure(it.body, "api")
                    is NetworkResponse.NetworkError -> handleFailure(it.error, "net")
                    is NetworkResponse.UnknownError -> handleFailure(it.error ?: Throwable(), "un")
                }
            }
        } catch (e: Exception) {
            _bankDetailsLiveEvent.value = Resource(throwable = e, status = State.ErrorState(e))
        }
    }

    private fun handleSuccess(data: BankDetailsEntity)  {
        _bankDetailsLiveEvent.value = Resource(data = data, status = State.DataState(data))
    }

    private fun handleFailure(throwable: Throwable, mes: String?) {
        _bankDetailsLiveEvent.value = Resource(throwable = throwable, status = State.ErrorState(throwable))
    }

}