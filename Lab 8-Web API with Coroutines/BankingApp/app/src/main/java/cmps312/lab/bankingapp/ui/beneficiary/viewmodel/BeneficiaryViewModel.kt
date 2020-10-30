package cmps312.lab.bankingapp.ui.beneficiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.reposiotry.BankRepository


class BeneficiaryViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val bankRepository = BankRepository(appContext)
    private var _beneficiaries = MutableLiveData<MutableList<Beneficiary>>();

    init {
        _beneficiaries.postValue(bankRepository.getBeneficiaries() as MutableList<Beneficiary>)
    }

    val beneficiaries = _beneficiaries
}