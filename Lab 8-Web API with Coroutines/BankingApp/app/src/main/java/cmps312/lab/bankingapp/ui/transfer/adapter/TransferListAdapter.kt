package cmps312.lab.bankingapp.ui.transfer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.ListItemTransferBinding
import cmps312.lab.bankingapp.model.Transfer

class TransferListAdapter(
    private val itemSelectedListener: (Transfer) -> Unit
) : RecyclerView.Adapter<TransferListAdapter.TransferViewHolder>() {

    var transfers =  listOf<Transfer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class TransferViewHolder(private val viewBinding: ListItemTransferBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(transfer: Transfer) {
            viewBinding.transfer = transfer
            viewBinding.root.setOnClickListener { itemSelectedListener(transfer) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {
        val viewBinding: ListItemTransferBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_transfer, parent, false
            )
        return TransferViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) =
        holder.bind(transfers[position])

    override fun getItemCount() = transfers.size
}