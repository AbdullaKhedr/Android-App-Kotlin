package cmps312.lab.bankingapp.ui.transfer.viewmodel

import android.app.Application
import androidx.lifecycle.*
import cmps312.lab.bankingapp.data.respository.BankRepository
import cmps312.lab.bankingapp.model.Transfer
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class TransferViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val TAG = "TransferViewModel"

    //you got all the accounts from the server
    //todo 1
    val accounts = liveData {
        emit(BankRepository.bankService.getAccounts(BankRepository.customerId))
    }

    // Initialize using liveData builder
    //todo 2
    private var _transfers = getTransfer() as MutableLiveData

    val transfers = _transfers as LiveData<List<Transfer>>

    fun getTransfer(cid : Int = BankRepository.customerId) = liveData {
        emit(BankRepository.bankService.getTransfer(cid))
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

        viewModelScope.launch {
            val addedTransfer = async { BankRepository.bankService.addTransfer(BankRepository.customerId, transfer)}
            _transfers.value?.let {
                _transfers.value = it + addedTransfer.await()
            }
        }
    }

    fun deleteTransfer(transfer: Transfer) {
        viewModelScope.launch {
            val successMessage = async { BankRepository.bankService.deleteTransfer(BankRepository.customerId, transfer.transferId)}

                _transfers.value?.let {
                    _transfers.value = it -  transfer
                }


        }
    }
}