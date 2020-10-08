package com.cmps312.countryvisitsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmps312.countryvisitsapp.R
import com.cmps312.countryvisitsapp.repository.Repository
import kotlinx.android.synthetic.main.fragment_add_visit.*

class AddVisitFragment : Fragment(R.layout.fragment_add_visit) {

    private val visitedCountryViewModel: VisitedCountryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countriesNames = Repository.countries.map { it.name }
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1, countriesNames
        )
        countryNameSpinner.adapter = adapter

        addBtn.setOnClickListener {
            
        }

    }
}