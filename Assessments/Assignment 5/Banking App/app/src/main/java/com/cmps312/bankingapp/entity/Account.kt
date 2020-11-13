package com.cmps312.bankingapp.entity

import com.google.firebase.firestore.DocumentId

data class Account(
    @DocumentId
    var accountNumber: String = "",
    var name: String = "",
    var acctType: String = "",
    var balance: Double = 0.0
) {
    override fun toString(): String {
        return accountNumber
    }
}
