package cmps312.lab.bankingapp.ui.transfer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cmps312.lab.bankingapp.model.Account
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.reposiotry.BankRepository

class TransferViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val TAG = "TransferViewModel"

    private val bankRepository = BankRepository(appContext)


    val _accounts  = MutableLiveData<List<Account>>()
    val accounts = _accounts

    // Initialize using liveData builder
    private var _transfers = MutableLiveData<MutableList<Transfer>>()

    val transfers = _transfers as LiveData<List<Transfer>>

    init {
        _transfers.postValue(bankRepository.getTransfers() as MutableList<Transfer>?)
        _accounts.postValue(bankRepository.getAccounts() as MutableList<Account>?)
    }

    //when item is selected from TransferListFragment->TransferDetailsFragment
    lateinit var selectedTransfer: Transfer

    // used for holding the details of new Transfer - used instead of Nav Component nav args
    lateinit var newTransfer: Transfer


    fun setTransferFromDetails(fromAccount: String, amount: Double) {
        newTransfer = Transfer(fromAccount, amount)
    }

    fun setTransferBeneficiaryDetails(beneficiaryName: String, beneficiaryAccountNo: String) {
        newTransfer.beneficiaryName = beneficiaryName
        newTransfer.beneficiaryAccountNo = beneficiaryAccountNo
    }

    //used by TransferConfirmationFragment
    fun addTransfer(transfer: Transfer = newTransfer) {
        _transfers.value?.let {
            it.add(transfer)
            _transfers.postValue(it)
        }
    }

    fun deleteTransfer(transfer: Transfer) {
        _transfers.value?.let {
            it.remove(transfer)
            _transfers.postValue(it)
        }
    }
}