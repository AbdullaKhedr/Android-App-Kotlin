package com.cmps312.payroll.model

class CommissionEmployee(
    firstName: String,
    lastName: String,
    qid: String,
    var grossSales: Double,
    var commissionRate: Double
) : Employee(firstName, lastName, qid) {
    init {
        if (grossSales < 0) {
            println("Invalid Gross Sales")
            grossSales = 0.0
        }
        if (commissionRate < 0) {
            println("Invalid Commission Rate")
            commissionRate = 0.0
        }
    }

    override fun getPaymentAmount() = grossSales * commissionRate

    override fun toString(): String {
        return """
        ${super.toString()}
        Gross Sales: $grossSales
        Commission Rate: $commissionRate
        """.trimIndent()
    }
}