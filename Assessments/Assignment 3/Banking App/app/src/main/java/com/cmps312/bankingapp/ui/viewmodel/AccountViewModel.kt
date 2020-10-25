package com.cmps312.bankingapp.ui.viewmodel

import androidx.lifecycle.*
import com.cmps312.bankingapp.data.repository.AccountRepository
import com.cmps312.bankingapp.model.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel : ViewModel() {

    private val _accounts = liveData {
        emit(AccountRepository.getAccounts())
    } as MutableLiveData
    var accountToEdit = Account()

    val accounts = _accounts as LiveData<List<Account>>

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.deleteAccount(account.accountNo)
            }
            _accounts.value?.let {
                _accounts.value = it - account
            }
        }
    }

    fun addAccount(newAccount: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.addAccount(newAccount)
            }
            _accounts.value?.let {
                _accounts.value = it + newAccount
            }
        }
    }

    fun updateAccount(editedAccount: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                AccountRepository.updateAccount(
                    editedAccount.accountNo,
                    editedAccount
                )
            }
            clearToEditAccount()
        }
    }

    fun clearToEditAccount() {
        accountToEdit = Account()
    }
}