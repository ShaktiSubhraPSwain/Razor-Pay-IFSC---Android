package com.example.razorpayifsc.data.mapper

import com.example.razorpayifsc.data.entity.BankDetailsApiEntity
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity


/**
 * Mapper - BankDetailsResponseEntity to BankDetailsEntity
 */
fun BankDetailsApiEntity.toBankDetailsEntity(): BankDetailsEntity {
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