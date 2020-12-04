package com.cmps312.learningpackageeditorapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cmps312.learningpackageeditorapp.R
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment(R.layout.fragment_start) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }
    }
}