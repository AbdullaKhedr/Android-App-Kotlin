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

    private val _accounts = MutableLiveData<List<Account>>()
    var accounts: LiveData<List<Account>> = _accounts

    init {
        accountsListener()
    }

    fun accountsListener() {
        AccountRepository.projectsCollectionRef.addSnapshotListener { snapshot, error ->
            if(error!=null)return@addSnapshotListener
            _accounts.value = snapshot!!.toObjects(Account::class.java)
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
                AccountRepository.addAccount(account)
            }
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                AccountRepository.deleteAccount(account)
            }
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //AccountRepository.updateAccount(account)
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            //AccountRepository.addTransaction(transaction)
        }
    }

    fun getAccounts(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            accounts = if (type == "All")
//                AccountRepository.getAllAccounts()
//            else
//                AccountRepository.getAccounts(type)
        }
    }
}