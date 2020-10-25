package com.cmps312.bankingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.ui.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : Fragment(R.layout.fragment_add_account) {
    private val accountViewModel by viewModels<AccountViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isEditing()) {
            textView11.text = "Edit Account"
            accountViewModel.currentAccount
        }

        typeSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.account_type)
        )

        var name = nameEdt.text.toString()
        var accountNo = numberEdt.text.toString()
        var accountType = typeSpinner.selectedItem.toString()
        var balance = 2

        doneBtn.setOnClickListener {
            if (name.isEmpty() || accountNo.isEmpty() || balance < 0) {
                Toast.makeText(context, "Please Fill all the requirements", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (isEditing()) {
                    accountViewModel.updateAccount(Account(accountNo, name, accountType, balance))
                }
                accountViewModel.addAccount(Account(accountNo, name, accountType, balance))
                activity?.onBackPressed()
            }
        }

        cancelBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun isEditing(): Boolean {
        return (accountViewModel.currentAccount != null)
    }
}