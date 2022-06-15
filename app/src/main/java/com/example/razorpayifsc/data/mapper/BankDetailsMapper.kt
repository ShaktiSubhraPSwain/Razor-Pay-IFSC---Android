package com.example.razorpayifsc.data.mapper

import com.example.razorpayifsc.data.entity.BankDetailsResponseEntity
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity

object BankDetailsMapper {

    fun BankDetailsResponseEntity.toDomain(): BankDetailsEntity {
        return BankDetailsEntity(
            bank = bank,
            ifsc = ifsc,
            branch = branch,
            address = address,
            contact = contact,
            city = city,
            district = district,
            state = state,
            bankCode = bankcode,
            )
    }
}