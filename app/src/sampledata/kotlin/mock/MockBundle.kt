package com.example.razorpayifsc.mock

import android.os.Bundle
import io.mockk.every
import io.mockk.mockk

/**
 * Mocked the Bundle
 */
fun mockBundleOf(vararg pairs: Pair<String, Any?>): Bundle {
    val bundle = mockk<Bundle>()

    for ((key, value) in pairs) {
        when (value) {
            is Boolean -> every { bundle.getBoolean(key) } returns value
            is Byte -> every { bundle.getByte(key) } returns value
            is Char -> every { bundle.getChar(key) } returns value
            is Double -> every { bundle.getDouble(key) } returns value
            is Float -> every { bundle.getFloat(key) } returns value
            is Int -> every { bundle.getInt(key) } returns value
            is Long -> every { bundle.getLong(key) } returns value
            is Short -> every { bundle.getShort(key) } returns value
            is String -> every { bundle.getString(key) } returns value
            else -> throw UnsupportedOperationException("Type is not supported.")
        }
    }
    return bundle
}
