package com.cmps312.learningpackageeditorapp.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.cmps312.learningpackageeditorapp.model.Word
import com.cmps312.learningpackageeditorapp.view.viewmodel.LearningPackagesViewModel
import kotlinx.android.synthetic.main.fragment_add_new_package.*

class AddNewPackageFragment : Fragment(R.layout.fragment_add_new_package) {
    private val learningPackagesViewModel: LearningPackagesViewModel by activityViewModels()
    private var mediaUrl: String = ""
    private var mediaExt = General.ResourceTypeEnum.NONE
    private var media = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        setMedia(uri)
        context.toast("Uri: $uri")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerLanguageAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.package_language)
        )
        spinnerLanguage.adapter = spinnerLanguageAdapter

        val spinnerLevelAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.package_level)
        )
        spinnerLevel.adapter = spinnerLevelAdapter

        cancelBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        nextBtn.setOnClickListener {
            if (validateInputs()) {
                learningPackagesViewModel.currentPackage.apply {
                    title = pkgTitle.text.toString()
                    description = pkgDescription.text.toString()
                    category = pkgCategory.text.toString()
                    language = spinnerLanguage.selectedItem.toString()
                    level = spinnerLevel.selectedItem.toString()
                    media = mediaUrl
                    mediaType = mediaExt
                }
                findNavController().navigate(R.id.action_addNewPackageFragment_to_addWordsFragment)
            }
        }

        newPackMultiMediaImg.setOnClickListener {
            media.launch("image/*")
        }

        newPackMultiMediaVideo.setOnClickListener {
            media.launch("video/*")
        }

        if (learningPackagesViewModel.currentPackage.packageID.isNotEmpty()) {
            onPackageEditRequired()
        }

        learningPackagesViewModel.currentWord = Word()
    }

    private fun setMedia(uri: Uri?) {
        mediaUrl = uri.toString()
        if (uri != null && uri.toString().contains("image")) {
            mediaExt = General.ResourceTypeEnum.IMAGE
            mediaImg.setImageURI(uri)
            mediaVideo.visibility = View.GONE
            mediaImg.visibility = View.VISIBLE
        } else if (uri != null) {
            mediaExt = General.ResourceTypeEnum.VIDEO
            mediaVideo.visibility = View.VISIBLE
            mediaImg.visibility = View.GONE
            mediaVideo.start()
            mediaVideo.setOnClickListener {
                if ((it as VideoView).isPlaying)
                    it.pause()
                else
                    it.start()
            }
            mediaVideo.setVideoURI(uri)
        }
    }

    private fun validateInputs(): Boolean {
        return if (pkgTitle.text.isBlank() || pkgCategory.text.isBlank() || pkgDescription.text.isBlank()
        ) {
            context.toast("Please fill all fields")
            false
        } else if (mediaExt == General.ResourceTypeEnum.NONE && newPackMultiMediaUrl.text.toString()
                .isNotBlank()
        ) {
            mediaExt = General.ResourceTypeEnum.URL
            mediaUrl = newPackMultiMediaUrl.text.toString()
            true
        } else
            true
    }

    private fun onPackageEditRequired() {
        learningPackagesViewModel.currentPackage.apply {
            pkgTitle.setText(title)
            pkgCategory.setText(category)
            pkgDescription.setText(description)
            if (mediaType != General.ResourceTypeEnum.NONE) {
                when (mediaType) {
                    General.ResourceTypeEnum.URL -> newPackMultiMediaUrl.setText(media)
                    else -> setMedia(Uri.parse(media))
                }
            }
            when (language) {
                "English" -> spinnerLanguage.setSelection(0)
                "Arabic" -> spinnerLanguage.setSelection(1)
            }
            when (level) {
                "Easy" -> spinnerLevel.setSelection(0)
                "Average" -> spinnerLevel.setSelection(1)
                "Difficult" -> spinnerLevel.setSelection(2)
            }
        }
        add_Edit_Pack_Title_Tv.text = "Edit Learning Package"
    }
}