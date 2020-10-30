package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.ui.transfer.adapter.TransferListAdapter
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_transfer_list.*

class TransferListFragment : Fragment(R.layout.fragment_transfer_list) {
    //we need this shared vew model because during transfer we will update it
    private val transferViewModel: TransferViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transferAdapter: TransferListAdapter
        transactionRV.apply {
            layoutManager = LinearLayoutManager(view.context)
            transferAdapter = TransferListAdapter(::onTransferSelected)
            adapter = transferAdapter
        }

        transferViewModel.transfers.observe(viewLifecycleOwner) {
            transferListProgress.visibility = View.GONE
            transferAdapter.transfers = it
        }

        setRecyclerSwipeListener()
    }

    //region Handle swipe to delete
    private fun setRecyclerSwipeListener() {
        /*
        1. Create ItemTouchHelper.SimpleCallback and tell it what events to listen for.
        It takes two parameters: One for drag directions and one for swipe directions.
        We’re only interested in swipe. Pass 0 to inform the callback not to respond to drag events.
        */
        val swipeHandler = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            // Ignore and do not perform any special behavior here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            // 2. onSwiped ask the RecyclerView adapter to delete the swiped item
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                onTransferDeleted(viewHolder.adapterPosition)
            }
        }

        /* 3. Initialize ItemTouchHelper with the swipeHandler you defined,
              and then attach it to the RecyclerView.
         */
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(transactionRV)
    }
    //endregion

    private fun onTransferSelected(transfer: Transfer) {
        transferViewModel.selectedTransfer = transfer
        findNavController().navigate(R.id.toTransferDetails)
    }

    private fun onTransferDeleted(position: Int) {
        transferViewModel.transfers.value?.let {
            val transfer = it[position]
            transferViewModel.deleteTransfer(transfer)

            Snackbar.make(requireView(), "$transfer removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO") {
                    transferViewModel.addTransfer(transfer)
                }.show()
        }
    }
}