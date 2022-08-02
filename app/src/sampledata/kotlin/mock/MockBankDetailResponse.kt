package com.example.razorpayifsc.mock

import com.example.razorpayifsc.BANK_DETAIL_JSON
import com.example.razorpayifsc.data.entity.BankDetailsApiEntity
import com.google.gson.Gson

val gson = Gson()
fun buildBankDetailResponse(): BankDetailsApiEntity =
    gson.fromJson(BANK_DETAIL_JSON, BankDetailsApiEntity::class.java)
