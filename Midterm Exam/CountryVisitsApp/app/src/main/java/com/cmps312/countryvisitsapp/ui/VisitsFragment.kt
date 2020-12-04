package com.cmps312.countryvisitsapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.countryvisitsapp.R
import com.cmps312.countryvisitsapp.repository.Repository
import kotlinx.android.synthetic.main.fragment_visits.*

class VisitsFragment : Fragment(R.layout.fragment_visits) {

    lateinit var visitedCountryAdapter: VisitedCountryListAdapter
    private val visitedCountryViewModel: VisitedCountryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        visitedCountryViewModel.visitedCountries().observe(viewLifecycleOwner) {
            visitedCountryAdapter.visitedCountries = it
        }

        fabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_visitsFragment_to_addVisitFragment)
        }

    }

    private fun initRecyclerView() {
        val countries = Repository.getVisitedCountries(requireContext()).toMutableList()

        visitedCountryAdapter = VisitedCountryListAdapter(::onCountryDeleted)
        listRV.apply {
            adapter = visitedCountryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onCountryDeleted(viewHolder: RecyclerView.ViewHolder) {
        visitedCountryAdapter.deleteCountry(viewHolder)
    }
}