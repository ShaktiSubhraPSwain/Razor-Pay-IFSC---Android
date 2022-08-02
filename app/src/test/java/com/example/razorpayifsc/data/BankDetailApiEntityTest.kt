package com.example.razorpayifsc.data

import com.example.razorpayifsc.data.mapper.toBankDetailsEntity
import com.example.razorpayifsc.domain.bank_details.model.BankDetailsEntity
import com.example.razorpayifsc.mock.buildBankDetailResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class BankDetailApiEntityTest {

    @Test
    fun `maps BankDetailResponse to BankDetail`() {
        val apiEntity = buildBankDetailResponse()
        val entity = apiEntity.toBankDetailsEntity()
        assertBankDetail(entity)
    }

    private fun assertBankDetail(entity: BankDetailsEntity) = with(entity) {
        assertEquals("ICICI Bank", bank)
        assertEquals("ICIC0000361", ifsc)
        assertEquals("AHMEDABAD-BOPAL", branch)
        assertEquals("AHMEDABAD", address)
        assertEquals("", contact)
        assertEquals("AHMEDABAD", city)
        assertEquals("AHMADABAD", district)
        assertEquals("GUJARAT", state)
        assertEquals("ICIC", bankCode)
    }
}
