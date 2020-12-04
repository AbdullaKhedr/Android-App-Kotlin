package com.cmps312.learningpackageeditorapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.model.LearningPackage
import com.cmps312.learningpackageeditorapp.view.adapter.LearningPackagesListAdapter
import com.cmps312.learningpackageeditorapp.view.viewmodel.LearningPackagesViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_all_packages_list.*

class AllPackagesListFragment : Fragment(R.layout.fragment_all_packages_list) {

    //private lateinit var navController: NavController
    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var learningPackageAdapter: LearningPackagesListAdapter
    private val learningPackagesViewModel: LearningPackagesViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar1)

        learningPackageAdapter =
            LearningPackagesListAdapter(::onDeleteLearningPackage, ::onPackageEdit)
        initRecycleView()

        fabBtn.setOnClickListener {
            val learningPackage = LearningPackage()
            learningPackagesViewModel.currentPackage = learningPackage
            findNavController().navigate(R.id.action_allPackagesListFragment_to_addNewPackageFragment)
        }

        learningPackagesViewModel.learningPackages.observe(viewLifecycleOwner) {
            learningPackageAdapter.learningPackages = it
        }
    }

    private fun initRecycleView() {
        all_pack_list_Rv.apply {
            adapter = learningPackageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onDeleteLearningPackage(learningPackage: LearningPackage) {
        showDeleteAlertDialog(learningPackage)
    }

    private fun onPackageEdit(viewHolder: RecyclerView.ViewHolder) {
        val packToEdit =
            learningPackageAdapter.editLearningPackage(viewHolder) // get the package position in the Rv
        learningPackagesViewModel.currentPackage = packToEdit
        findNavController().navigate(R.id.action_allPackagesListFragment_to_addNewPackageFragment)
    }

    private fun showDeleteAlertDialog(learningPackage: LearningPackage) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Delete Confirmation!")
        builder.setMessage("Are you sure to delete this package?")

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    learningPackagesViewModel.deleteLearningPackage(learningPackage)
                    learningPackageAdapter.updateRv() // this used to happen by the viewModel, but we change the deletion method there to implement the update.
                }
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNegativeButton("NO", dialogClickListener)

        dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_option_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_option_search).actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(searchHandler)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val searchHandler = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false
        override fun onQueryTextChange(searchText: String): Boolean {
            learningPackageAdapter.searchFilter(searchText)
            return true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_log_out -> {
                // to make rememberMe btn is unchecked again
                val MY_PREFERENCES = "rememberMePrefs"
                val REMEMBER_ME_CHECK_BOX = "rememberMeCheckBoxState"
                val sharedPref =
                    this.activity?.getSharedPreferences(
                        MY_PREFERENCES,
                        AppCompatActivity.MODE_PRIVATE
                    )
                sharedPref?.edit()?.putBoolean(REMEMBER_ME_CHECK_BOX, false)?.apply()
                // sign out from firebase
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_allPackagesListFragment_to_startFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}