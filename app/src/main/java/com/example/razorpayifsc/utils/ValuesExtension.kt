@file:Suppress("NOTHING_TO_INLINE")

package com.example.razorpayifsc.utils

/**
 * Returns the defaultValue if it's null otherwise return the value
 */

inline fun Boolean?.value(defaultValue: Boolean = false): Boolean = this ?: defaultValue

inline fun CharSequence?.value(defaultValue: CharSequence = ""): CharSequence = this ?: defaultValue
