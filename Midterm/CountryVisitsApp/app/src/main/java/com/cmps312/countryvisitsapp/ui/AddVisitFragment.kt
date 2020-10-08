package com.cmps312.countryvisitsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cmps312.countryvisitsapp.R
import com.cmps312.countryvisitsapp.model.VisitedCountry
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

            val selectedCountryCode =
                Repository.countries.find { countryNameSpinner.selectedItem.toString() == it.name }
            val newVisitedCountry = VisitedCountry(
                selectedCountryCode?.code.toString(),
                countryNameSpinner.selectedItem.toString(),
                ratingBar2.rating,
                amountEdt.text.toString().toFloat()
            )
            visitedCountryViewModel.newVisitedCountry = newVisitedCountry
            visitedCountryViewModel.addCountry()

            findNavController().navigate(R.id.action_addVisitFragment_to_visitsFragment)
        }

    }
}