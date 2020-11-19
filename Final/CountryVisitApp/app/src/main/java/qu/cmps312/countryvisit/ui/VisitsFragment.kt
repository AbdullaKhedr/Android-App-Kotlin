package qu.cmps312.countryvisit.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import qu.cmps312.countryvisit.R
import qu.cmps312.countryvisit.model.Visit
import kotlinx.android.synthetic.main.fragment_visits.*
import qu.cmps312.countryvisit.ui.adapter.VisitAdapter
import qu.cmps312.countryvisit.ui.viewmodel.VisitsViewModel

class VisitsFragment : Fragment(R.layout.fragment_visits) {

    private val visitsViewModel: VisitsViewModel by activityViewModels()
    private lateinit var visitAdapter: VisitAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addVisitFab.setOnClickListener {
            visitsViewModel.isEdit = false
            findNavController().navigate(R.id.toVisitEditorFragment)
        }

    }

    private fun initRecyclerView() {
        visitAdapter = VisitAdapter(::onVisitDeleted, ::onVisitUpdated)
        listRV.apply {
            adapter = visitAdapter
            layoutManager = LinearLayoutManager(context)
        }
        visitsViewModel.visitsList.observe(viewLifecycleOwner) {
            visitAdapter.visits = it
        }
    }

    private fun onVisitDeleted(visit: Visit) {
        visitsViewModel.currentVisit = visit
        visitsViewModel.deleteVisit()
    }

    private fun onVisitUpdated(visit: Visit) {
        visitsViewModel.isEdit = true
        visitsViewModel.currentVisit = visit
        findNavController().navigate(R.id.toVisitEditorFragment)
    }
}