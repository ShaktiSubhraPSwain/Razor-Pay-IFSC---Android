package com.example.razorpayifsc

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.utils.APIConst
import com.google.gson.Gson
import io.mockk.* // ktlint-disable no-wildcard-imports

const val BANK_DETAIL_JSON = "{\n" +
        "  \"MICR\": \"380229012\",\n" +
        "  \"BRANCH\": \"AHMEDABAD-BOPAL\",\n" +
        "  \"ADDRESS\": \"AHMEDABAD\",\n" +
        "  \"STATE\": \"GUJARAT\",\n" +
        "  \"CONTACT\": \"\",\n" +
        "  \"UPI\": true,\n" +
        "  \"RTGS\": true,\n" +
        "  \"CITY\": \"AHMEDABAD\",\n" +
        "  \"CENTRE\": \"AHMADABAD\",\n" +
        "  \"DISTRICT\": \"AHMADABAD\",\n" +
        "  \"NEFT\": true,\n" +
        "  \"IMPS\": true,\n" +
        "  \"SWIFT\": \"\",\n" +
        "  \"ISO3166\": \"IN-GJ\",\n" +
        "  \"BANK\": \"ICICI Bank\",\n" +
        "  \"BANKCODE\": \"ICIC\",\n" +
        "  \"IFSC\": \"ICIC0000361\"\n" +
        "}"

val gson = Gson()
fun bankDetailResponse(): BankDetailsEntity =
    gson.fromJson(BANK_DETAIL_JSON, BankDetailsResponseEntity::class.java).toDomain()

val hashMap = hashMapOf(
    APIConst.ifscCode to "ICIC0000361"
)

const val MOCK_IFSC_CODE = "ICIC0000361"
const val NOT_FOUND = "Not found"

// API response code
const val SUCCESS_CODE = 200
const val BAD_REQUEST_CODE = 400

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

/**
 * Mock FirebaseAnalytics
 */
fun mockBankAnalytics(): BankAnalytics {
    val bankAnalytics = mockk<BankAnalytics>()

    every { bankAnalytics.logEvent(any(), any()) } just Runs
    return bankAnalytics
}

/**
 * Captured the datum of observer and returned the list of data
 */
inline fun <reified T : Any> Observer<T>.captureObserverChanges(list: ArrayList<T>): List<T> {
    // create slot
    val slot = slot<T>()

    // capture value on every call
    every { onChanged(capture(slot)) } answers {
        // store captured value
        list.add(slot.captured)
    }

    return list
}
