package cmps312.lab.bankingapp.ui.transfer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cmps312.lab.bankingapp.R
import kotlinx.android.synthetic.main.fragment_transfer.*

class TransferFragment : Fragment(R.layout.fragment_transfer) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo initialize the empty transfer

//        fromAccountSp.adapter = ArrayAdapter<Account>(
//            view.context,
//            android.R.layout.simple_dropdown_item_1line,
//            //todo add the accounts from the model
//        )

        nextBtn.setOnClickListener {
            //todo [update the new transfer]
            findNavController().navigate(R.id.toSelectBeneficiary)
        }
    }
}