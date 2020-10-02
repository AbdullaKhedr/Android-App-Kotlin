package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.ui.transfer.adapter.TransferListAdapter
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import kotlinx.android.synthetic.main.fragment_transfer_list.*

class TransferListFragment : Fragment(R.layout.fragment_transfer_list) {
    //we need this shared vew model because during transfer we will update it
    //todo 1. get a refrence of the shared view model
    val transferViewModel : TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val transferAdapter = TransferListAdapter(::onTransferSelected)

        transactionRV.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = transferAdapter
        }

       //todo 2 start observing
        transferViewModel.transfers().observe(viewLifecycleOwner){
            //as soon as the data is populated this method will be called and you will be given it[_transfers]
            transferAdapter.transfers = it
        }
    }

    private fun onTransferSelected(transfer: Transfer) {
        transferViewModel.selectedTransfer = transfer
        findNavController().navigate(R.id.toTransferDetails)
    }
}