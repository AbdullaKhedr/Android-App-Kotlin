package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.FragmentTransferConfirmationBinding
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel


class TransferConfirmationFragment : Fragment(R.layout.fragment_transfer_confirmation) {
    //once the layout is properly inflated then we can access the view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain an instance of the view binding class.
        val viewBinding = FragmentTransferConfirmationBinding.bind(view)
        // Obtain an instance of the ViewModel
        val transferViewModel  by activityViewModels<TransferViewModel>()

        viewBinding.transfer = transferViewModel.newTransfer

        viewBinding.confirmationBtn.setOnClickListener {
            transferViewModel.addTransfer()
            findNavController().navigate(R.id.toHome)
        }

        viewBinding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.transferFragment)
        }
    }
}