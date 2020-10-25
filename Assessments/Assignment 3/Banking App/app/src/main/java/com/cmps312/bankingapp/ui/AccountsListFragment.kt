package com.cmps312.bankingapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.model.Account
import com.cmps312.bankingapp.ui.adapter.AccountAdapter
import com.cmps312.bankingapp.ui.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_accounts_list.*

class AccountsListFragment : Fragment(R.layout.fragment_accounts_list) {
    private val accountViewModel by viewModels<AccountViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()



        addFab.setOnClickListener {
            findNavController().navigate(R.id.action_accountsListFragment_to_addAccountFragment)
        }

    }

    private fun initRecyclerView() {
        val accountAdapter = AccountAdapter(::deleteAccountListener, ::editAccountListener)
        accountsRv.apply {
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(context)
        }
//        accountViewModel.accounts.observe(viewLifecycleOwner) {
//            accountAdapter.accounts = it as MutableList
//        }
    }

    private fun deleteAccountListener(account: Account) {
        //beneficiaryViewModel.deleteBeneficiary(beneficiary)
    }

    private fun editAccountListener(account: Account) {
        //beneficiaryViewModel.deleteBeneficiary(beneficiary)
    }
}