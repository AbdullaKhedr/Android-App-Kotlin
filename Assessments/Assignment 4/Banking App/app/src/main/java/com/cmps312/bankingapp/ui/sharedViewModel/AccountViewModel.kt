package com.cmps312.bankingapp.ui.sharedViewModel

import android.app.Application
import androidx.lifecycle.*
import com.cmps312.bankingapp.repository.AccountRepository
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val accountRepository by lazy { AccountRepository(application) }
    var accounts = accountRepository.getAccounts()

    //transaction
    var selectedAccountForTransaction = Account()

    //will be used for both edit and update
    var account = Account()
    var isEdit = false

    fun addAccount() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                accountRepository.addAccount(account)
            }
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                accountRepository.deleteAccount(account)
            }
        }
    }

    fun updateAccount() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                accountRepository.updateAccount(account)
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.addTransaction(transaction)
        }
    }

    fun getAccountsByType(type: String) = accountRepository.getAccountsByType(type)
}