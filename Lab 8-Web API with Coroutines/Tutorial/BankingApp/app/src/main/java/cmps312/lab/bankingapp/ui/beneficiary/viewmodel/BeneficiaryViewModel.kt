package cmps312.lab.bankingapp.ui.beneficiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import cmps312.lab.bankingapp.data.respository.BankRepository


class BeneficiaryViewModel(appContext: Application) : AndroidViewModel(appContext) {

    var beneficiaries =  liveData {
        emit(BankRepository.bankService.getBeneficiaries(BankRepository.customerId))
    }
}