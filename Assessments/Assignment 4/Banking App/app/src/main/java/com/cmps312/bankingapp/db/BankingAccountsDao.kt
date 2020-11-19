package com.cmps312.bankingapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction

@Dao
interface BankingAccountsDao {

    @Query("SELECT * FROM Account WHERE acctType =:type")
    suspend fun getAccounts(type: String): List<Account>

    @Query("SELECT * FROM Account")
    suspend fun getAllAccounts(): List<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(account: Account): Long

    @Delete
    suspend fun deleteAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: Transaction): Long
}
