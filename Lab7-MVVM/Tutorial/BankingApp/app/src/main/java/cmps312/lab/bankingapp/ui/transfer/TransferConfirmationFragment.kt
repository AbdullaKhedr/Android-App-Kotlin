package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.databinding.FragmentTransferConfirmationBinding
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel


class TransferConfirmationFragment : Fragment(R.layout.fragment_transfer_confirmation) {
    //once the layout is properly inflated then we can access the view
    val transferViewModel : TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTransferConfirmationBinding.bind(view)
        binding.newTransfer = transferViewModel.newTransfer

        binding.confirmationBtn.setOnClickListener {
            transferViewModel.addTransfer();
            findNavController().navigate(R.id.toHome)
        }

    }
}