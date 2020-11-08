package com.cmps312.bankingapp.repository

import android.content.Context
import com.cmps312.bankingapp.db.BankingAccountsDatabase
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction


class AccountRepository(private val context: Context) {

    private val bankingAccountsDao by lazy {
        BankingAccountsDatabase.getDatabase(context).bankingAccountsDao()
    }

    fun getAccounts(type: String) = bankingAccountsDao.getAccounts(type)
    fun getAllAccounts() = bankingAccountsDao.getAllAccounts()
    suspend fun addAccount(account: Account) = bankingAccountsDao.addAccount(account)
    suspend fun updateAccount(account: Account) = bankingAccountsDao.updateAccount(account)
    suspend fun deleteAccount(account: Account) = bankingAccountsDao.deleteAccount(account)
    suspend fun addTransaction(transaction: Transaction) =
        bankingAccountsDao.addTransaction(transaction)

}