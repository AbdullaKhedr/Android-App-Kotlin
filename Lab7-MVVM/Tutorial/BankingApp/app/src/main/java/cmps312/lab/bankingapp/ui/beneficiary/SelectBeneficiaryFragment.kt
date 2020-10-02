package cmps312.lab.bankingapp.ui.beneficiary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.ui.beneficiary.adapter.BeneficiaryAdapter
import cmps312.lab.bankingapp.ui.beneficiary.viewmodel.SelectBeneficiaryViewModel
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import kotlinx.android.synthetic.main.fragment_select_beneficiary.*

class SelectBeneficiaryFragment : Fragment(R.layout.fragment_select_beneficiary) {

    val selectBeneficiaryViewModel : SelectBeneficiaryViewModel by viewModels()
    val transferViewModel : TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beneficiaryAdapter = BeneficiaryAdapter(::oneBeneficiarySelected)
        beneficiaryRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = beneficiaryAdapter
        }


        selectBeneficiaryViewModel.beneficiaries().observe(viewLifecycleOwner){
            beneficiaryAdapter.beneficiaries = it
        }
    }

    private fun oneBeneficiarySelected(beneficiary: Beneficiary) {
        transferViewModel.newTransfer.apply {
                beneficiaryName = beneficiary.name
                beneficiaryAccountNo = beneficiary.accountNo
        }
        findNavController().navigate(R.id.toTransferConfirmation)
    }
}