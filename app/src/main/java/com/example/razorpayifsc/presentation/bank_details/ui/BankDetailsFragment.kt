package com.example.razorpayifsc.presentation.bank_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.razorpayifsc.presentation.base.SafeObserver
import com.example.razorpayifsc.presentation.base.State
import com.example.razorpayifsc.presentation.base.Resource
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.razorpayifsc.R
import com.example.razorpayifsc.databinding.FragmentBankBinding
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.presentation.bank_details.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.utils.callbacks.NetworkStateManager
import com.example.razorpayifsc.presentation.dialogs.ErrorDialogFragment
import com.example.razorpayifsc.utils.*

@AndroidEntryPoint
class BankDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBankBinding
    private val viewModel by viewModels<BankDetailsViewModel>()
    private var networkStateManager: NetworkStateManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.hide()
        viewListener()
        initLiveDataObservers()
    }

    private fun initLiveDataObservers() {
        viewModel.bankDetailsLiveEvent.observe(
            viewLifecycleOwner,
            SafeObserver(this::handleBankDetailsResponse)
        )
        initNetworkState()
    }

    private fun initNetworkState() {
        networkStateManager = NetworkStateManager.getInstance()
        networkStateManager?.getConnectivityStatus()?.observe(
            viewLifecycleOwner,
            SafeObserver(this::handleNetworkState)
        )
    }

    // Update button enable status on network connectivity status
    private fun handleNetworkState(status: Boolean) {
        binding.btnSubmit.isEnabled =
            viewModel.buttonEnableStatus(status, binding.etIfscCode.text).value()
    }

    private fun viewListener() {
        binding.btnSubmit.setOnClickListener(this)
        binding.etIfscCode.addTextChangedListener(
            afterTextChanged = { text ->
                binding.tilIfscCode.isEndIconVisible = text?.isNotEmpty().value()
            },
            onTextChanged = { s, _, _, _ ->
                binding.btnSubmit.isEnabled =
                    viewModel.buttonEnableStatus(
                        networkStateManager?.isInternetAvailable, s
                    ).value()
            }
        )
    }

    private fun handleBankDetailsResponse(response: Resource<BankDetailsEntity>) {
        when (response.status) {
            is State.LoadingState -> {
                binding.progressBar.show()
                binding.tableBankDetails.hide()
            }
            is State.ErrorState -> handleBankDetailsFailure(response)
            is State.DataState -> handleSuccessResponse(response.data)
        }
    }

    /**
     * Handling success response of ifsc code request and setting the data in views
     */
    private fun handleSuccessResponse(response: BankDetailsEntity?) {
        binding.progressBar.hide()
        binding.tableBankDetails.show()
        response?.let {
            // setting data to view
            with(binding) {
                tvBankName.text = it.bank
                tvBranchName.text = it.branch
                tvBankAddress.text = it.address
                tvBankCity.text = it.city
                tvBankDistrict.text = it.district
                tvBankState.text = it.state
                tvBankCode.text = it.bankCode
                // If contact no is null or empty set not available
                tvContactNo.text = it.contact.value(getString(R.string.not_available))
            }
        }
    }

    /**
     *  Handling error response of ifsc code request
     */
    private fun handleBankDetailsFailure(response: Resource<BankDetailsEntity>) {
        binding.progressBar.hide()
        // Hide the bank details
        binding.tableBankDetails.hide()
        response.throwable?.let { error ->
            ErrorDialogFragment().show(requireActivity().supportFragmentManager, error.message)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnSubmit.id -> {
                hideKeyboard()
                viewModel.fetchBankDetails(binding.etIfscCode.toText())
            }
        }
    }
}
