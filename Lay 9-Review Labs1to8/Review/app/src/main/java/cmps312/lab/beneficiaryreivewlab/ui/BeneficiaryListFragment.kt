package cmps312.lab.beneficiaryreivewlab.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.beneficiaryreivewlab.R
import cmps312.lab.beneficiaryreivewlab.model.Beneficiary
import cmps312.lab.beneficiaryreivewlab.ui.adapter.BeneficiaryAdapter
import cmps312.lab.beneficiaryreivewlab.ui.viewmodel.BeneficiaryViewModel
import kotlinx.android.synthetic.main.fragment_beneficiary_list.*


class BeneficiaryListFragment : Fragment(R.layout.fragment_beneficiary_list) {

    private val beneficiaryViewModel : BeneficiaryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beneficiaryAdapter = BeneficiaryAdapter(::deleteBeneficiaryListener)
        beneficiaryRV.apply {
            adapter = beneficiaryAdapter
            layoutManager = LinearLayoutManager(context)
        }

        beneficiaryViewModel.beneficiaries.observe(viewLifecycleOwner){
            beneficiaryAdapter.beneficiaries = it
        }

        addBtn.setOnClickListener {
            findNavController().navigate(R.id.toAddBeneficiaryFragment)
        }
    }

    fun deleteBeneficiaryListener(beneficiary: Beneficiary){
        beneficiaryViewModel.deleteBeneficiary(beneficiary)
    }
}