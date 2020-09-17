package com.cmps312.payroll.model

abstract class Employee(
    val firstName: String,
    val lastName: String,
    val qid: String
) : Payable {
    override fun toString(): String {
        return """
            First Name: $firstName
            Last Name: $lastName
            QID: $qid
        """.trimIndent()
    }
}