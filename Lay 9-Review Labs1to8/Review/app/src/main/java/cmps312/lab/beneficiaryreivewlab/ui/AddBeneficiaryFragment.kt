package cmps312.lab.beneficiaryreivewlab.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.beneficiaryreivewlab.R
import cmps312.lab.beneficiaryreivewlab.model.Beneficiary
import cmps312.lab.beneficiaryreivewlab.ui.viewmodel.BeneficiaryViewModel
import kotlinx.android.synthetic.main.fragment_add_beneficiary.*


class AddBeneficiaryFragment : Fragment(R.layout.fragment_add_beneficiary) {

    val beneficiaryViewModel : BeneficiaryViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn.setOnClickListener {
            val beneficiary = Beneficiary()
            //validation
            beneficiary.apply {
                name = nameEdt.text.toString()
                accountNo = accountNoEdt.text.toString()
                cid = cidEdt.text.toString().toInt()
            }
            beneficiaryViewModel.addBeneficiary(beneficiary)
            activity?.onBackPressed()

        }
    }

}