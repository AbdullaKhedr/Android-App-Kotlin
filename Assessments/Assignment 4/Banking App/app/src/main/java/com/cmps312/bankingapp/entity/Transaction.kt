package com.cmps312.bankingapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountNo"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var type: String = "",
    var amount: String = "",
    @ColumnInfo(index = true)
    var accountNo: String = "",
)
