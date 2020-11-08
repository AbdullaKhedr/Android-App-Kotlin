package com.cmps312.bankingapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["accountNumber"],
            childColumns = ["accountNo"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var type: String = "",
    var amount: Double = 0.0,
    @ColumnInfo(index = true)
    var accountNo: Int = 0,
)
