package com.cmps312.bankingapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cmps312.bankingapp.R
import kotlinx.android.synthetic.main.fragment_add_account.*

class AddAccountFragment : Fragment(R.layout.fragment_add_account) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEdt
        numberEdt
        typeSpinner
        balanceEdt

        doneBtn.setOnClickListener {

        }

        cancelBtn.setOnClickListener {

        }
    }
}