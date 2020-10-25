package com.cmps312.bankingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cmps312.bankingapp.data.repository.AccountRepository
import com.cmps312.bankingapp.model.Account

class AccountViewModel : ViewModel() {

    private val _countries = liveData {
        emit(AccountRepository.getAccounts())
    } as MutableLiveData

    val accounts = _countries as LiveData<List<Account>>
}