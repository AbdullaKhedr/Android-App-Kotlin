package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.FragmentTransferDetailsBinding
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel

class TransferDetailsFragment : Fragment(R.layout.fragment_transfer_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain an instance of the view binding class.
        val viewBinding = FragmentTransferDetailsBinding.bind(view)
        val transferViewModel : TransferViewModel by activityViewModels()
        viewBinding.transfer = transferViewModel.selectedTransfer
    }
}