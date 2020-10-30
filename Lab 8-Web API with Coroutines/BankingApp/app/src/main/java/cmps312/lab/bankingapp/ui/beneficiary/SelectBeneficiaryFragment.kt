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
import cmps312.lab.bankingapp.ui.beneficiary.viewmodel.BeneficiaryViewModel
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import kotlinx.android.synthetic.main.fragment_select_beneficiary.*

class SelectBeneficiaryFragment : Fragment(R.layout.fragment_select_beneficiary) {
    private val beneficiaryViewModel: BeneficiaryViewModel by viewModels()
    private val transferViewModel: TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //just to avoid the code from crushing as the selectedTransfer is a lateInit and it needs to be initialized
        val beneficiaryAdapter = BeneficiaryAdapter(::oneBeneficiarySelected)
        beneficiaryRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = beneficiaryAdapter
        }

        beneficiaryViewModel.beneficiaries.observe(viewLifecycleOwner) {
            beneficiaryAdapter.beneficiaries = it
            progressBarBenfit.visibility = View.GONE
        }
    }

    private fun oneBeneficiarySelected(beneficiary: Beneficiary) {
        transferViewModel.setTransferBeneficiaryDetails(beneficiary.name, beneficiary.accountNo)
        findNavController().navigate(R.id.toTransferConfirmation)
    }
}