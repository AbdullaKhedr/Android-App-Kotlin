package com.cmps312.payroll.model

class HourlyEmployee(
    firstName: String,
    lastName: String,
    qid: String,
    var wage: Double,
    var hours: Double
) : Employee(firstName, lastName, qid) {
    init {
        if (wage < 0) {
            println("Invalid Wage")
            wage = 0.0
        }
        if (hours < 0) {
            println("Invalid Hours")
            hours = 0.0
        }
    }

    override fun getPaymentAmount() = wage * hours

    override fun toString(): String {
        return """
        ${super.toString()}
        Wage: $wage
        Hours: $hours
        """.trimIndent()
    }
}