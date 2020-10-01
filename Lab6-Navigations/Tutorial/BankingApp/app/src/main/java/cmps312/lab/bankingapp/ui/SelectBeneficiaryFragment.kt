package cmps312.lab.bankingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cmps312.lab.bankingapp.R
import kotlinx.android.synthetic.main.fragment_select_beneficiary.*

class SelectBeneficiaryFragment : Fragment(R.layout.fragment_select_beneficiary) {

    private val args : SelectBeneficiaryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transfer = args.transfer

        nextBtn.setOnClickListener {
            transfer.beneficiaryAccountNo = beneficiaryAccountNoEt.text.toString();
            transfer.beneficiaryName = beneficiaryNameEt.text.toString();

            val action = SelectBeneficiaryFragmentDirections
                .actionSelectBeneficiaryFragmentToTransferConfirmationFragment(transfer)
            findNavController().navigate(action)
        }
    }
}