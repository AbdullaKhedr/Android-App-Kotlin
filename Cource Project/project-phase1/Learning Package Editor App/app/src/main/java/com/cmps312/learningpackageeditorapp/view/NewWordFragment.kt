package com.cmps312.learningpackageeditorapp.view

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmps312.learningpackageeditorapp.R
import com.cmps312.learningpackageeditorapp.common.General.toast
import com.cmps312.learningpackageeditorapp.model.Definition
import com.cmps312.learningpackageeditorapp.model.Sentence
import com.cmps312.learningpackageeditorapp.model.Word
import com.cmps312.learningpackageeditorapp.view.viewmodel.LearningPackagesViewModel
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.fragment_new_word.*
import kotlinx.android.synthetic.main.sent_def_item.view.*

class NewWordFragment : Fragment(R.layout.fragment_new_word) {
    private val learningPackagesViewModel: LearningPackagesViewModel by activityViewModels()
    private var editState = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (learningPackagesViewModel.currentWord.text.isNotBlank()) {
            editMode()
        } else
            editState = false

        newDefinitionEdt.addTextChangedListener { text ->
            newItem(
                newDefinitionEdt,
                text,
                " ",
                definitionsList
            )
        }

        newSentenceEdt.addTextChangedListener { text ->
            newItem(
                newSentenceEdt,
                text,
                "\n",
                sentencesList
            )
        }

        addSentBtn.setOnClickListener {
            if (newSentenceEdt.text.toString().isNotBlank()) {
                newItem(newSentenceEdt, newSentenceEdt.text, "", sentencesList)
                context.toast("Sentence Added Successfully")
            } else
                context.toast("Please write a Sentence first!")
        }

        addDefBtn.setOnClickListener {
            if (newDefinitionEdt.text.toString().isNotBlank()) {
                newItem(newDefinitionEdt, newDefinitionEdt.text, "", definitionsList)
                context.toast("Definition Added Successfully")
            } else
                context.toast("Please write a Definition first!")
        }

        submitNewWordBtn.setOnClickListener {
            if (newWordEdt.text.toString() != "") {
                learningPackagesViewModel.addWord(newWordEdt.text.toString())
                learningPackagesViewModel.currentWord = Word()
                activity?.onBackPressed()
            } else
                context.toast("Please write a Word first!")
        }

        cancelNewWordBtn.setOnClickListener {
            learningPackagesViewModel.currentWord = Word()
            editState = false
            activity?.onBackPressed()
        }
    }

    private fun editMode() {
        editState = true
        learningPackagesViewModel.currentWord.apply {
            newWordEdt.setText(text)
        }

        textView10.text = "Edit word"

        populateData()
    }

    private fun populateData() {
        var item: View
        if (learningPackagesViewModel.currentWord.definitions.isNotEmpty()) {
            learningPackagesViewModel.currentWord.definitions.forEach { definition ->
                item = View.inflate(requireContext(), R.layout.sent_def_item, null)
                item.content.text = definition.text
                item.closeBtn.setOnClickListener {
                    definitionsList.removeView(it.parent.parent as View)
                    learningPackagesViewModel.currentWord.definitions - definition
                }

                item.setOnClickListener {
                    if (newDefinitionEdt.text.toString().isBlank()) {
                        newDefinitionEdt.setText(it.content.text, TextView.BufferType.EDITABLE)
                        definitionsList.removeView(it as View)
                        learningPackagesViewModel.currentWord.definitions - definition
                    }
                }
                definitionsList.addView(item)
            }
        }

        if (learningPackagesViewModel.currentWord.sentences.isNotEmpty()) {
            learningPackagesViewModel.currentWord.sentences.forEach { sentence ->
                item = View.inflate(requireContext(), R.layout.sent_def_item, null)
                item.content.text = sentence.text.trim()
                item.closeBtn.setOnClickListener {
                    sentencesList.removeView(it.parent.parent as View)
                    learningPackagesViewModel.currentWord.sentences - sentence
                }

                item.setOnClickListener {
                    if (newSentenceEdt.text.toString().isBlank()) {
                        newSentenceEdt.setText(it.content.text, TextView.BufferType.EDITABLE)
                        sentencesList.removeView(it as View)
                        learningPackagesViewModel.currentWord.sentences - sentence
                    }
                }
                sentencesList.addView(item)
            }
        }
    }

    private fun newItem(field: EditText, text: Editable?, delimiter: String, flex: FlexboxLayout) {
        if (text.isNullOrBlank())
            return

        val item = View.inflate(requireContext(), R.layout.sent_def_item, null)
        if ((text.toString().last().toString()) == delimiter || delimiter.isEmpty()) {
            var currentItem: Any? = null
            when (field.id) {
                R.id.newDefinitionEdt -> {
                    currentItem = Definition(field.text.toString())
                    if (editState)
                        learningPackagesViewModel.currentWord.definitions + currentItem
                    else
                        learningPackagesViewModel.addDefinition(currentItem)
                }

                R.id.newSentenceEdt -> {
                    currentItem = Sentence(field.text.toString())
                    if (editState)
                        learningPackagesViewModel.currentWord.sentences + currentItem
                    else
                        learningPackagesViewModel.addSentence(currentItem)
                }
            }

            item.setOnClickListener {
                if (field.text.toString().isBlank()) {
                    field.setText(item.content.text, TextView.BufferType.EDITABLE)
                    flex.removeView(it as View)
                    removeFromWord(field, currentItem)
                }
            }

            item.content.text = text.toString().trim()
            item.closeBtn.setOnClickListener {
                flex.removeView(it.parent.parent as View)
                removeFromWord(field, currentItem)
            }

            flex.addView(item)
            field.text = null
        }
    }

    private fun removeFromWord(field: EditText, currentItem: Any?) {
        when (field.id) {
            R.id.newDefinitionEdt -> {
                if (editState)
                    learningPackagesViewModel.currentWord.definitions - currentItem
                else
                    learningPackagesViewModel.deleteDefinition(currentItem as Definition)
            }
            R.id.newSentenceEdt -> {
                if (editState)
                    learningPackagesViewModel.currentWord.sentences - currentItem
                else
                    learningPackagesViewModel.deleteSentence(currentItem as Sentence)
            }
        }
    }
}