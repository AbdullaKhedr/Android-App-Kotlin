package com.cmps312.learningpackageeditorapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.cmps312.learningpackageeditorapp.model.Word
import com.cmps312.learningpackageeditorapp.repository.Repository
import com.cmps312.learningpackageeditorapp.view.adapter.WordsAdapter
import com.cmps312.learningpackageeditorapp.view.viewmodel.LearningPackagesViewModel
import kotlinx.android.synthetic.main.fragment_add_words.*
import java.text.SimpleDateFormat
import java.util.*

class AddWordsFragment : Fragment(R.layout.fragment_add_words) {

    private val learningPackagesViewModel: LearningPackagesViewModel by activityViewModels()
    private lateinit var wordsAdapter: WordsAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pack_title.text =
            getString(R.string.pkgWordsList, learningPackagesViewModel.currentPackage.title)

        initRecyclerView()

        learningPackagesViewModel.words.observe(viewLifecycleOwner) {
            wordsAdapter.words = it
        }

        if (learningPackagesViewModel.currentPackage.packageID.isNotEmpty()) {
            onPackageEditRequired()
        }

        addWordFabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addWordsFragment_to_newWordFragment)
        }

        submitPackagerBtn.setOnClickListener {
            Repository.getUsers(requireContext())
            learningPackagesViewModel.currentPackage.apply {
                if (packageID == "")
                    packageID = UUID.randomUUID().toString()
                authorName = "${Repository.users[0].firstName} ${Repository.users[0].lastName}"
                version = version.plus(1)
                context.toast("Version is: ${version.toString()}")
                lastUpdateDate = SimpleDateFormat("dd/M/yyyy").format(Date())
                keyWords = arrayListOf("KeyWord1", "KeyWord2")
            }
            learningPackagesViewModel.addLearningPackage()
            findNavController().navigate(R.id.action_addWordsFragment_to_allPackagesListFragment)
        }
    }

    private fun initRecyclerView() {
        wordsAdapter = WordsAdapter(::onWordDeleted, ::onWordClicked)
        wordsRv.apply {
            adapter = wordsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onWordClicked(word: Word) {
        learningPackagesViewModel.currentWord = word
        findNavController().navigate(R.id.action_addWordsFragment_to_newWordFragment)
    }

    private fun onWordDeleted(word: Word) {
        learningPackagesViewModel.deleteWord(word)
    }

    private fun onPackageEditRequired() {
        learningPackagesViewModel.clearWords()
        learningPackagesViewModel.currentPackage.apply {
            if (learningPackagesViewModel.words.value.isNullOrEmpty()) // this was needed to fix the problem in adding words while editing a package
                words.forEach { learningPackagesViewModel.addWord(it) }
        }
    }
}