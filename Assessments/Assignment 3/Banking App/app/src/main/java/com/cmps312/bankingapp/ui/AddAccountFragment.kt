package com.cmps312.bankingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.databinding.FragmentAddAccountBinding
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.ui.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : Fragment(R.layout.fragment_add_account) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.account_type)
        )

        if (isEditing()) {
            title.text = "Edit Account"

            val binding = FragmentAddAccountBinding.bind(view)
            binding.account = accountViewModel.accountToEdit

            if (accountViewModel.accountToEdit.acctType == "Current")
                typeSpinner.setSelection(0)
            else
                typeSpinner.setSelection(1)
        }

        doneBtn.setOnClickListener {
            if (nameEdt.text.isEmpty() || numberEdt.text.isEmpty() || balanceEdt.text.toString()
                    .toInt() < 0
            ) {
                Toast.makeText(context, "Please Fill all the requirements", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val name = nameEdt.text.toString()
                val accountNo = numberEdt.text.toString()
                val accountType = typeSpinner.selectedItem.toString()
                val balance = balanceEdt.text.toString().toInt()

                if (isEditing()) {
                    accountViewModel.accountToEdit.name = name
                    accountViewModel.accountToEdit.accountNo = accountNo
                    accountViewModel.accountToEdit.acctType = accountType
                    accountViewModel.accountToEdit.balance = balance
                    accountViewModel.updateAccount(accountViewModel.accountToEdit)
                } else {
                    accountViewModel.addAccount(Account(accountNo, name, accountType, balance))
                }
                activity?.onBackPressed()
            }
        }

        cancelBtn.setOnClickListener {
            accountViewModel.clearToEditAccount()
            activity?.onBackPressed()
        }
    }

    private fun isEditing(): Boolean {
        return (accountViewModel.accountToEdit.accountNo != "-1")
    }
}