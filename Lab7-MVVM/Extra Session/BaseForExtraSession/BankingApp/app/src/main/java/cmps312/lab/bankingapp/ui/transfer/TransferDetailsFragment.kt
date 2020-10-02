package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.FragmentTransferDetailsBinding
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel


class TransferDetailsFragment : Fragment(R.layout.fragment_transfer_details) {
    val transferViewModel: TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding =  FragmentTransferDetailsBinding.bind(view)
        binding.selectedTransfer = transferViewModel.selectedTransfer
    }
}