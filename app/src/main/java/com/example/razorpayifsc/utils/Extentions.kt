package com.example.razorpayifsc.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun EditText.toText(): String {
    return text.toString()
}

/// Snackbar Extension
fun View.showSnackbar(message: Int, color: Int, duration: Int): Snackbar {
    return Snackbar.make(
        this,
        message,
        duration
    ).apply {
        view.setBackgroundColor(color)
        show()
    }
}

fun View.showSnackbar(message: Int, color: Int): Snackbar {
    return showSnackbar(message, color, duration = Snackbar.LENGTH_LONG)
}
