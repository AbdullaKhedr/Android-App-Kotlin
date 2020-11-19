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
    val types = liveData<List<String>> { emit(listOf<String>("All", "Saving", "Current")) }
    var selectedType = MutableLiveData<String>()
    var accounts = selectedType.switchMap { type ->
        liveData {
            emit(
                if (type == "All")
                    accountRepository.getAllAccounts()
                else
                    accountRepository.getAccounts(type)
            )
        }
    }

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

    fun updateAccount(account: Account) {
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

}