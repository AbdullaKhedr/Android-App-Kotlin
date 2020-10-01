package cmps312.lab.bankingapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cmps312.lab.bankingapp.R
import kotlinx.android.synthetic.main.fragment_transaction_list.*

class TransactionListFragment : Fragment(R.layout.fragment_transaction_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fundTransferBtn.setOnClickListener {
            val action = TransactionListFragmentDirections.actionTransactionListFragmentToTransferFragment()
            findNavController().navigate(R.id.action_transactionListFragment_to_transferFragment)
        }
        transDetailsBtn.setOnClickListener {
            val action = TransactionListFragmentDirections
                .actionTransactionListFragmentToTransactionDetailsFragment()

            findNavController().navigate(action)
        }


    }
}