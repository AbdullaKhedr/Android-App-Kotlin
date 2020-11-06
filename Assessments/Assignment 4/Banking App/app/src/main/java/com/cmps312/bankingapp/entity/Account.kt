package com.cmps312.bankingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Account(
    @PrimaryKey(autoGenerate = true)
    var accountNo: String = "",
    var name: String = "",
    var acctType: String = "",
    var balance: Int = 0
) {
    override fun toString(): String {
        return accountNo
    }
}
