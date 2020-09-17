package com.cmps312.payroll.model

class SalariedEmployee(
    firstName: String,
    lastName: String,
    qid: String,
    var monthlySalary: Double
) : Employee(firstName, lastName, qid) {
    fun getSalary() = monthlySalary
    fun setSalary(salary: Double) {
        if (monthlySalary < 0) {
            println("Invalid Monthly Salary")
            monthlySalary = 0.0
        }
    }

    override fun getPaymentAmount() = monthlySalary

    override fun toString(): String {
        return """
            ${super.toString()}
            Monthly Salary: $monthlySalary
        """.trimIndent()
    }
}