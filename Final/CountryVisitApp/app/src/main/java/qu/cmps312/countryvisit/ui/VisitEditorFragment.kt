package qu.cmps312.countryvisit.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_visit_editor.*
import qu.cmps312.countryvisit.R
import qu.cmps312.countryvisit.databinding.FragmentVisitEditorBinding
import qu.cmps312.countryvisit.model.Continent
import qu.cmps312.countryvisit.model.Country
import qu.cmps312.countryvisit.model.Visit
import qu.cmps312.countryvisit.ui.viewmodel.VisitsViewModel

class VisitEditorFragment : Fragment(R.layout.fragment_visit_editor) {
    private val visitsViewModel: VisitsViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visitsViewModel.continents.observe(requireActivity()) {
            val adapter = ArrayAdapter<Continent>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            continentSp.adapter = adapter
        }

        continentSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedContinent = continentSp.selectedItem as Continent
                visitsViewModel.selectedContinent.value = selectedContinent
            }
        }

        visitsViewModel.countriesList.observe(requireActivity()) {
            val adapter = ArrayAdapter<Country>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            countrySp.adapter = adapter
        }

        if (visitsViewModel.isEdit) {
            Toast.makeText(requireContext(), visitsViewModel.currentVisit.name, Toast.LENGTH_SHORT)
                .show()
            val binding = FragmentVisitEditorBinding.bind(view)
            binding.editVisit = visitsViewModel.currentVisit
        }

        saveBtn.setOnClickListener {
            if (visitsViewModel.isEdit)
                visitsViewModel.updateVisit()
            else {
                Toast.makeText(
                    requireContext(),
                    visitsViewModel.currentVisit.name,
                    Toast.LENGTH_SHORT
                ).show()

                val selectedCountry = countrySp.selectedItem as Country
                val newVisit =
                    Visit(
                        selectedCountry.code,
                        selectedCountry.name,
                        visitRatingBar.rating,
                        amountEdt.text.toString().toFloat()
                    )
                visitsViewModel.currentVisit = newVisit
                visitsViewModel.addVisit()
            }

            activity?.onBackPressed()
        }

    }
}