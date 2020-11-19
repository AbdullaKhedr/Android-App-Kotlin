package com.cmps312.bankingapp.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_transaction.*


class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.accounts.observe(viewLifecycleOwner) {
            accountNoSp.adapter = ArrayAdapter<Account>(
                view.context,
                android.R.layout.simple_dropdown_item_1line,
                it  // error 2 as List<Account>
            )

        }
        accountNoSp.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                accountViewModel.selectedAccountForTransaction = accountNoSp.selectedItem as Account
                nameTv.text = accountViewModel.selectedAccountForTransaction.name
                balanceTv.text = accountViewModel.selectedAccountForTransaction.balance.toString()

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        val transaction = Transaction()

        submitBtn.setOnClickListener {
            val amount = amountEdt.text
            if (accountViewModel.selectedAccountForTransaction.accountNumber == 0) {
                Toast.makeText(context, "No Account Provided!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                when {
                    amount.isEmpty() -> Toast.makeText(
                        activity,
                        " You should provide a value for the amount to withdraw or deposit",
                        Toast.LENGTH_LONG
                    ).show()
                    (amount.toString()
                        .toDouble() > accountViewModel.selectedAccountForTransaction.balance)
                            && transactionTypeSp.selectedItem == "Withdraw" -> Toast.makeText(
                        activity,
                        "You do not have enough balance",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> {
                        if (transactionTypeSp.selectedItem == "Withdraw") {
                            accountViewModel.selectedAccountForTransaction.balance -= amount.toString()
                                .toDouble()
                            transaction.type = "Withdraw"
                        } else {
                            accountViewModel.selectedAccountForTransaction.balance += amount.toString()
                                .toDouble()
                            transaction.type = "Deposit"
                        }
                        transaction.amount = amount.toString().toDouble()
                        transaction.accountNo =
                            accountViewModel.selectedAccountForTransaction.accountNumber
                        transaction.id = 0
                        accountViewModel.addTransaction(transaction)
                        accountViewModel.updateAccount(accountViewModel.selectedAccountForTransaction)
                        activity?.onBackPressed()
                    }
                }
            }
        }

        cancel1Btn.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}