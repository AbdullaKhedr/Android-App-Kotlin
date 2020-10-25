package com.cmps312.bankingapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.ui.adapter.AccountAdapter
import com.cmps312.bankingapp.ui.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_accounts_list.*

class AccountsListFragment : Fragment(R.layout.fragment_accounts_list) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        addFab.setOnClickListener {
            // To make sure that is not editing mode
            accountViewModel.clearToEditAccount()
            findNavController().navigate(R.id.action_accountsListFragment_to_addAccountFragment)
        }
    }

    private fun initRecyclerView() {
        val accountAdapter = AccountAdapter(::deleteAccountListener, ::editAccountListener)
        accountsRv.apply {
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(context)
        }
        accountViewModel.accounts.observe(viewLifecycleOwner) {
            accountAdapter.accounts = it
        }
    }

    private fun deleteAccountListener(account: Account) {
        accountViewModel.deleteAccount(account)
    }

    private fun editAccountListener(account: Account) {
        accountViewModel.accountToEdit = account
        findNavController().navigate(R.id.action_accountsListFragment_to_addAccountFragment)
    }
}