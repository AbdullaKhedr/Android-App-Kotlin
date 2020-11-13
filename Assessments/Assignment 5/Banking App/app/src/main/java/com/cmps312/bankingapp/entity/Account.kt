package com.cmps312.bankingapp.entity

data class Account(
    var accountNumber: Int = 0,
    var name: String = "",
    var acctType: String = "",
    var balance: Double = 0.0
) {
    override fun toString(): String {
        return accountNumber.toString()
    }
}
