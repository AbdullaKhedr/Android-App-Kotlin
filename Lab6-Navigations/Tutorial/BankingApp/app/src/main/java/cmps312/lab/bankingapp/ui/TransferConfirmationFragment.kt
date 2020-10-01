package cmps312.lab.bankingapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import cmps312.lab.bankingapp.R
import kotlinx.android.synthetic.main.fragment_transfer_confirmation.*

class TransferConfirmationFragment : Fragment(R.layout.fragment_transfer_confirmation) {
    val args : TransferConfirmationFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.transfer.apply {
            fromAccountNoTv.text = fromAccountNo
        }
    }
}