package com.example.razorpayifsc.domain.usecases

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.google.gson.Gson

const val BANK_DETAIL_JSON = "{\n" +
        "    \"RTGS\": true,\n" +
        "    \"IMPS\": true,\n" +
        "    \"CENTRE\": \"DAKSHINA KANNADA\",\n" +
        "    \"NEFT\": true,\n" +
        "    \"BRANCH\": \"Karnataka Bank IMPS\",\n" +
        "    \"MICR\": \"\",\n" +
        "    \"CITY\": \"MUMBAI\",\n" +
        "    \"STATE\": \"MAHARASHTRA\",\n" +
        "    \"ISO3166\": \"IN-MH\",\n" +
        "    \"DISTRICT\": \"DAKSHINA KANNADA\",\n" +
        "    \"CONTACT\": \"+918242228222\",\n" +
        "    \"UPI\": true,\n" +
        "    \"ADDRESS\": \"REGD. & HEAD OFFICE, P.B.NO.599, MAHAVEER CIRCLE, KANKANADY, MANGALORE - 575002\",\n" +
        "    \"SWIFT\": \"\",\n" +
        "    \"BANK\": \"Karnataka Bank\",\n" +
        "    \"BANKCODE\": \"KARB\",\n" +
        "    \"IFSC\": \"KARB0000001\"\n" +
        "}"

val gson = Gson()
fun bankDetailResponse() : BankDetailsResponseEntity = gson.fromJson(BANK_DETAIL_JSON, BankDetailsResponseEntity::class.java)
val hashMap = HashMap<String, String>().apply {
    put("ifscCode", "KARB0000001")
}

const val MOCK_IFSC_CODE = "KARB0000001"