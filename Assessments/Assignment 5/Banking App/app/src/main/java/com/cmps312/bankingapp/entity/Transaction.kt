package com.cmps312.bankingapp.entity

import com.google.firebase.firestore.DocumentId

data class Transaction(
    @DocumentId
    var id: String = "",
    var type: String = "",
    var amount: Double = 0.0,
    var accountNo: String = "",
)
