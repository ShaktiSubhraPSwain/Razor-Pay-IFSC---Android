package com.example.razorpayifsc.data.entity

import com.google.gson.annotations.SerializedName

data class BankDetailsApiEntity(
    @SerializedName("BANK")
    val bank: String = "",
    @SerializedName("IFSC")
    val ifsc: String = "",
    @SerializedName("BRANCH")
    val branch: String = "",
    @SerializedName("ADDRESS")
    val address: String = "",
    @SerializedName("CONTACT")
    val contact: String?,
    @SerializedName("CITY")
    val city: String = "",
    @SerializedName("DISTRICT")
    val district: String = "",
    @SerializedName("STATE")
    val state: String = "",
    @SerializedName("RTGS")
    val rtgs: Boolean = false,
    @SerializedName("BANKCODE")
    val bankCode: String = "",
)

