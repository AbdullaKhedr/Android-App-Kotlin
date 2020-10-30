package cmps312.lab.bankingapp.ui.beneficiary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.ListItemBeneficiaryBinding
import cmps312.lab.bankingapp.model.Beneficiary

class BeneficiaryAdapter(
    private val itemSelectedListener: (Beneficiary) -> Unit
) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {
    var beneficiaries = listOf<Beneficiary>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class BeneficiaryViewHolder(private val viewBinding: ListItemBeneficiaryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(beneficiary: Beneficiary) {
            viewBinding.beneficiary = beneficiary
            viewBinding.root.setOnClickListener { itemSelectedListener(beneficiary) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val viewBinding: ListItemBeneficiaryBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_beneficiary, parent, false
            )
        return BeneficiaryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) =
        holder.bind(beneficiaries[position])

    override fun getItemCount() = beneficiaries.size
}