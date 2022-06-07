package com.example.razorpayifsc.bankDetails.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankDetailsEntity(
    @Expose
    @SerializedName("BANK")
    val bank: String = "",
    @Expose
    @SerializedName("IFSC")
    val ifsc: String = "",
    @Expose
    @SerializedName("BRANCH")
    val branch: String = "",
    @Expose
    @SerializedName("ADDRESS")
    val address: String = "",
    @Expose
    @SerializedName("CONTACT")
    val contact: String?,
    @Expose
    @SerializedName("CITY")
    val city: String = "",
    @Expose
    @SerializedName("DISTRICT")
    val district: String = "",
    @Expose
    @SerializedName("STATE")
    val state: String = "",
    @Expose
    @SerializedName("RTGS")
    val rtgs: Boolean = false,
    @Expose
    @SerializedName("BANKCODE")
    val bankcode: String = "",
)