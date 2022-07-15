package com.example.razorpayifsc.data.entity

import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.google.gson.annotations.SerializedName

data class BankDetailsResponseEntity(
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
) {
    /**
     * Mapper - BankDetailsResponseEntity to BankDetailsEntity
     */
    fun toDomain(): BankDetailsEntity {
        return BankDetailsEntity(
            bank = bank,
            ifsc = ifsc,
            branch = branch,
            address = address,
            contact = contact,
            city = city,
            district = district,
            state = state,
            bankCode = bankCode,
        )
    }
}
