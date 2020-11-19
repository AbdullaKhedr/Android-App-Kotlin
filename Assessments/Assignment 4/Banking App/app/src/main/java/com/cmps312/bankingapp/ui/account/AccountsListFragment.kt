package com.cmps312.bankingapp.ui.account

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmps312.bankingapp.R
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.ui.account.adapter.AccountAdapter
import com.cmps312.bankingapp.ui.sharedViewModel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_accounts_list.*

class AccountsListFragment : Fragment(R.layout.fragment_accounts_list) {
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        addFab.setOnClickListener {
            accountViewModel.isEdit = false
            findNavController().navigate(R.id.action_accountsListFragment_to_addAccountFragment)
        }
        accountViewModel.types.observe(requireActivity()){
            val adapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_text, it
            )
            filterSP.adapter = adapter
        }


        filterSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                accountViewModel.selectedType.value = filterSP.selectedItem.toString()
                //initRecyclerView()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

    }

    private fun initRecyclerView() {
        val accountAdapter = AccountAdapter(::deleteAccountListener, ::editAccountListener)
        accountsRv.apply {
            adapter = accountAdapter
            layoutManager = LinearLayoutManager(context)
        }
        accountViewModel.accounts.observe(requireActivity()) {
            accountAdapter.accounts = it // error 1  as List<Account>
        }
    }

    private fun deleteAccountListener(account: Account) {
        accountViewModel.deleteAccount(account)
    }

    private fun editAccountListener(account: Account) {
        accountViewModel.isEdit = true
        accountViewModel.account = account
        findNavController().navigate(R.id.action_accountsListFragment_to_addAccountFragment)
    }
}