package cmps312.lab.bankingapp.ui.beneficiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.bankingapp.R

import cmps312.lab.bankingapp.model.Beneficiary
import kotlinx.android.synthetic.main.list_item_beneficiary.view.*

class BeneficiaryAdapter(
    private val itemSelectedListener: (Beneficiary) -> Unit
) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {
    var beneficiaries: List<Beneficiary>? = null
        set(beneficiaries) {
            field = beneficiaries;
            notifyDataSetChanged()
        }

    inner class BeneficiaryViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(beneficiary: Beneficiary) {
            itemView.beneficiaryNameTv.text = beneficiary.name

            itemView.setOnClickListener {
                itemSelectedListener(beneficiary)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_beneficiary, parent, false)
        return BeneficiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) =
        holder.bind(beneficiaries!![position])

    override fun getItemCount() = beneficiaries?.size ?: 0
}