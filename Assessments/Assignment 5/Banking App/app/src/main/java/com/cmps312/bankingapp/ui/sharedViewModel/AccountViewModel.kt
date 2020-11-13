package com.cmps312.bankingapp.ui.sharedViewModel

import android.app.Application
import androidx.lifecycle.*
import com.cmps312.bankingapp.repository.AccountRepository
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val _accounts = MutableLiveData<List<Account>>()
    var accounts: LiveData<List<Account>> = _accounts
    private var accountsUpdateListener: ListenerRegistration? = null

    // account in a transaction
    var selectedAccountForTransaction = Account()

    // will be used for both edit and update a user
    var account = Account()
    var isEdit = false

    init {
        registerListeners()
    }

    fun registerListeners() {
        accountsListener()
    }

    fun unRegisterListeners() {
        accountsUpdateListener?.remove()
    }

    private fun accountsListener() {
        accountsUpdateListener?.remove()
        accountsUpdateListener = AccountRepository.projectsCollectionRef
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                _accounts.value = snapshot!!.toObjects(Account::class.java)
            }
    }

    fun addAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            AccountRepository.addAccount(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            AccountRepository.deleteAccount(account)
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            AccountRepository.updateAccount(account)
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            AccountRepository.addTransaction(transaction)
        }
    }

    override fun onCleared() {
        accountsUpdateListener?.remove()
        super.onCleared()
    }

    fun getAccounts(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            accountsUpdateListener = AccountRepository
                .projectsCollectionRef
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener
                    _accounts.value = if (type == "All")
                        snapshot!!.toObjects(Account::class.java)
                    else
                        snapshot!!.toObjects(Account::class.java).filter { it.acctType == type }
                }
        }
    }
}