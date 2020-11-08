package com.cmps312.bankingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey(autoGenerate = true)
    var accountNumber: Int = 0,
    var name: String = "",
    var acctType: String = "",
    var balance: Double = 0.0
) {
    override fun toString(): String {
        return accountNumber.toString()
    }
}
