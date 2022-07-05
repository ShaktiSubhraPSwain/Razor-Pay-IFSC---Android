package com.example.razorpayifsc.presentation.bankDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.razorpayifsc.presentation.base.SafeObserver
import com.example.razorpayifsc.presentation.State
import com.example.razorpayifsc.presentation.base.Resource
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.razorpayifsc.R
import com.example.razorpayifsc.databinding.FragmentBankBinding
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.presentation.bankDetails.viewmodel.BankDetailsViewModel
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager
import com.example.razorpayifsc.presentation.dialogs.ErrorDialogFragment
import com.example.razorpayifsc.utils.*

@AndroidEntryPoint
class BankDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBankBinding
    var viewModel: BankDetailsViewModel? = null
    var networkStateManager: NetworkStateManager? = null

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
        viewModel = (viewModel ?: ViewModelProvider(this)[BankDetailsViewModel::class.java])
        binding.progressBar.hide()
        viewListener()
        initLiveDataObservers()
    }

    private fun initLiveDataObservers() {
        viewModel?.bankDetailsLiveEvent?.observe(
            viewLifecycleOwner,
            SafeObserver(this::handleBankDetailsResponse)
        )
        initNetworkState()
    }


    private fun initNetworkState() {
        networkStateManager = NetworkStateManager.getInstance()
        networkStateManager?.getConnectivityStatus()?.observe(
            this,
            SafeObserver(this::handleNetworkState)
        )
    }

    /// Update button enable status on network connectivity status
    private fun handleNetworkState(status: Boolean) {
        binding.btnSubmit.isEnabled =
            status && binding.etIfscCode.text?.isNotEmpty() ?: false
    }

    private fun viewListener() {
        binding.btnSubmit.setOnClickListener(this)

        // Here
        binding.etIfscCode.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.btnSubmit.isEnabled =
                    (networkStateManager?.isInternetAvailable ?: false) && s.isNotEmpty()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                binding.tilIfscCode.isEndIconVisible = s.isNotEmpty()
            }
        })
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
            binding.apply {
                tvBankName.text = it.bank
                tvBranchName.text = it.branch
                tvBankAddress.text = it.address
                tvBankCity.text = it.city
                tvBankDistrict.text = it.district
                tvBankState.text = it.state
                tvBankCode.text = it.bankCode
                // If contact no is null or empty set not available
                tvContactNo.text =
                    if (it.contact.isNullOrEmpty()) getString(R.string.not_available) else it.contact
            }
        }
    }

    /**
     *  Handling error response of ifsc code request
     */
    private fun handleBankDetailsFailure(response: Resource<BankDetailsEntity>) {
        binding.progressBar.hide()
        /// Hide the bank
        binding.tableBankDetails.hide()
        activity?.let {
            response.throwable.let { error ->
                activity?.let { activity ->
                    ErrorDialogFragment().show(activity.supportFragmentManager, error?.message)

                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnSubmit.id -> {
                hideKeyboard()
                viewModel?.fetchBankDetails(binding.etIfscCode.toText())
            }
        }
    }
}