package com.example.razorpayifsc.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.razorpayifsc.R
import com.example.razorpayifsc.databinding.ErrorDialogBinding

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = ErrorDialogBinding.inflate(inflater, null, false)
            view.error.playAnimation()
            builder.setView(view.root)
                .setNegativeButton(R.string.ok) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
