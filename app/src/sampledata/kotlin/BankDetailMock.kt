package com.example.razorpayifsc

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.google.gson.Gson

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
fun bankDetailResponse() : BankDetailsResponseEntity = gson.fromJson(BANK_DETAIL_JSON, BankDetailsResponseEntity::class.java)
val hashMap = HashMap<String, String>().apply {
    put("ifscCode", "ICIC0000361")
}

const val MOCK_IFSC_CODE = "ICIC0000361"