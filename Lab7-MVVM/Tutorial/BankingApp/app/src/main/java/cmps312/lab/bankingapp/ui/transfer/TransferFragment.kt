package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cmps312.lab.bankingapp.R
import cmps312.lab.bankingapp.model.Account
import cmps312.lab.bankingapp.model.Transfer
import cmps312.lab.bankingapp.ui.transfer.viewmodel.TransferViewModel
import kotlinx.android.synthetic.main.fragment_transfer.*
import kotlinx.android.synthetic.main.fragment_transfer.view.*

class TransferFragment : Fragment(R.layout.fragment_transfer) {

    val transferViewModel : TransferViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //empty transfer
        transferViewModel.newTransfer = Transfer()

        fromAccountSp.adapter = ArrayAdapter<Account>(
            view.context,
            android.R.layout.simple_dropdown_item_1line,
            transferViewModel.accounts
        )

        nextBtn.setOnClickListener {
            transferViewModel.apply{
                newTransfer.amount = amountEt.text.toString().toDouble()
                newTransfer.fromAccountNo = fromAccountSp.selectedItem.toString()
            }
            findNavController().navigate(R.id.toSelectBeneficiary)
        }
    }
}