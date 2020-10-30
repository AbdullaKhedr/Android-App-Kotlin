package cmps312.lab.beneficiaryreivewlab.ui.viewmodel

import androidx.lifecycle.*
import cmps312.lab.beneficiaryreivewlab.data.repository.BeneficiaryRepo
import cmps312.lab.beneficiaryreivewlab.model.Beneficiary
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BeneficiaryViewModel : ViewModel(){
    private val _beneficiaries = getBeneficiariesFromRepo() as MutableLiveData

    val beneficiaries = _beneficiaries as LiveData<List<Beneficiary>>

    private fun getBeneficiariesFromRepo() = liveData {
        emit(BeneficiaryRepo.getBeneficiaries())
    }

    fun addBeneficiary(beneficiary: Beneficiary){

        viewModelScope.launch {
            val addedBeneficiary = async { BeneficiaryRepo.addBeneficiary(beneficiary) }.await()

            _beneficiaries.value?.let {
                _beneficiaries.value = it + addedBeneficiary
            }
        }


    }

    fun deleteBeneficiary(beneficiary: Beneficiary){
        viewModelScope.launch {
             val successMessage = async { BeneficiaryRepo.deleteBeneficiary(beneficiary.accountNo) }.await()

            _beneficiaries.value?.let {
                _beneficiaries.value = it - beneficiary
            }
        }

    }
}