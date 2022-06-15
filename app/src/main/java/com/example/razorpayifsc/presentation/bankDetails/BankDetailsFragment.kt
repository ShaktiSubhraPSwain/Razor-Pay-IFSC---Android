package com.example.razorpayifsc.presentation.bankDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.razorpayifsc.presentation.base.SafeObserver
import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.databinding.FragmentBankBinding
import com.example.razorpayifsc.presentation.State
import com.example.razorpayifsc.presentation.base.Resource
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable

import android.text.TextWatcher
import com.example.razorpayifsc.R
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.presentation.callbacks.NetworkStateManager


@AndroidEntryPoint
class BankDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBankBinding
    private val viewModel: BankdetailsViewModel by viewModels()
    lateinit var networkStateManager: NetworkStateManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBankBinding.inflate(inflater, container, false)
        viewListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.isVisible = false
        initLiveDataObservers()
    }

    private fun initLiveDataObservers() {
        binding.progressBar.hide()
        viewModel.bankDetailsLiveEvent.observe(
            viewLifecycleOwner,
            SafeObserver(this::handleBankDetailsResponse)
        )
        initNetworkState()
    }


    private fun initNetworkState() {
        networkStateManager = NetworkStateManager.getInstance()!!
        networkStateManager.getConnectivityStatus().observe(
            this,
            SafeObserver(this::handleNetworkState)
        )
    }

    private fun handleNetworkState(status: Boolean) {
        binding.submitBtn.isEnabled =
            status && binding.ifscCodeEditText.text.toString().isNotEmpty()
    }

    private fun viewListener() {
        binding.submitBtn.setOnClickListener(this)

        binding.ifscCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.submitBtn.isEnabled =
                    networkStateManager.isInternetAvailable && s.toString().isNotEmpty()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun handleBankDetailsResponse(response: Resource<BankDetailsEntity>) {
        when (response.status) {
            is State.LoadingState -> binding.progressBar.isVisible = true
            is State.ErrorState -> handleBankDetails(response)
            is State.DataState -> handleRandomSuccess(response.data)
        }
    }

    private fun handleRandomSuccess(response: BankDetailsEntity?) {
        binding.progressBar.isVisible = false
        binding.bankDetailsTable.isVisible = true
        response?.let {
            binding.apply {
                bankName.text = it.bank
                branchNameTv.text = it.branch
                bankAddressTv.text = it.address
                bankCityTv.text = it.city
                bankDistrictTv.text = it.district
                bankStateTv.text = it.state
                bankCodeTv.text = it.bankCode
                contactNoTv.text = if (!it.contact.isNullOrEmpty()) it.contact else getString(R.string.notAvailable)
            }
        }
    }

    private fun handleBankDetails(response: Resource<BankDetailsEntity>) {
        binding.progressBar.hide()
        binding.bankDetailsTable.isVisible = false
        response.throwable?.let {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.submitBtn.id -> viewModel.fetchBankDetails(binding.ifscCodeEditText.text.toString())
        }
    }
}