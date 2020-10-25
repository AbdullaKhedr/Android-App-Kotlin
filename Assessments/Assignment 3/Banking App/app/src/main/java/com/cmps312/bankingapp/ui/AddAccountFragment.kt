package com.cmps312.bankingapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.databinding.FragmentAddAccountBinding
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.ui.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : Fragment(R.layout.fragment_add_account) {
    private val accountViewModel by viewModels<AccountViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isEditing()) {
            title.text = "Edit Account"
            //val binding = FragmentAddAccountBinding.bind(view)
            //binding.account = accountViewModel.accountToEdit
        }

        typeSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.account_type)
        )

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
                    accountViewModel.updateAccount(Account(accountNo, name, accountType, balance))
                } else {
                    accountViewModel.addAccount(Account(accountNo, name, accountType, balance))
                }
                activity?.onBackPressed()
            }
        }

        cancelBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun isEditing(): Boolean {
        Log.i(TAG, "isEditing: ${accountViewModel.accountToEdit.accountNo != "-1"}")
        return (accountViewModel.accountToEdit.accountNo != "-1")
    }
}