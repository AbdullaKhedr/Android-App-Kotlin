package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.ui.transfer.adapter.TransferListAdapter
import kotlinx.android.synthetic.main.fragment_transfer_list.*

class TransferListFragment : Fragment(R.layout.fragment_transfer_list) {
    //we need this shared vew model because during transfer we will update it
    //todo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transferAdapter : TransferListAdapter
        transactionRV.apply {
            layoutManager = LinearLayoutManager(view.context)
            transferAdapter = TransferListAdapter(::onTransferSelected)
            adapter = transferAdapter
        }

        //todo
    }

    private fun onTransferSelected(transfer: Transfer) {
        //todo
        findNavController().navigate(R.id.toTransferDetails)
    }
}