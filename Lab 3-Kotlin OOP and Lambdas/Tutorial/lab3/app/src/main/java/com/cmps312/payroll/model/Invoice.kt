package com.cmps312.payroll.model

class Invoice(
    val partNumber: String,
    val partDescription: String,
    var quantity: Int,
    var unitPrice: Double
) : Payable {
    init {
        if (quantity < 0)
            quantity = 0
        if (unitPrice < 0)
            unitPrice = 0.0
    }

    override fun getPaymentAmount(): Double {
        return quantity * unitPrice
    }

    override fun toString(): String {
        return """
            Part number: $partNumber
            Part description: $partDescription 
            Quantity: $quantity
            Unit price: $unitPrice
        """.trimIndent()
    }
}