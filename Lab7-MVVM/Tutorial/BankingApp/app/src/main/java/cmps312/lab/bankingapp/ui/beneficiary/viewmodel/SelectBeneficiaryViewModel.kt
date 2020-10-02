package cmps312.lab.bankingapp.ui.beneficiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.reposiotry.BankRepository

class SelectBeneficiaryViewModel(application: Application) : AndroidViewModel(application) {

    private var _beneficiaries = MutableLiveData<MutableList<Beneficiary>>();

    init {
        _beneficiaries.postValue(BankRepository.getBeneficiaries(application) as MutableList<Beneficiary>)
    }

    fun beneficiaries(): LiveData<MutableList<Beneficiary>> = _beneficiaries
}