package cmps312.lab.bankingapp.ui.transfer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.bankingapp.R

import cmps312.lab.bankingapp.model.Transfer

class TransferListAdapter(private val itemSelectedListener: (Transfer) -> Unit) :
    RecyclerView.Adapter<TransferListAdapter.TransferViewHolder>() {

    var transfers : List<Transfer>? = null
    set(transfers) {
        field = transfers
        notifyDataSetChanged()
    }

    inner class TransferViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(transfer: Transfer) {
          //todo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {

        val view =  LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_transfer, parent, false
        )

        return TransferViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) =
        holder.bind(transfers!![position])

    override fun getItemCount() = transfers?.size ?: 0
}