package com.cmps312.bankingapp.entity

data class Transaction(
    var id: Int = 0,
    var type: String = "",
    var amount: Double = 0.0,
    var accountNo: Int = 0,
)
