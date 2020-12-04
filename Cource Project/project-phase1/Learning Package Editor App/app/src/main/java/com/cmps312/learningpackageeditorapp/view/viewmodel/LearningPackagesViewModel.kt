package com.cmps312.learningpackageeditorapp.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.cmps312.learningpackageeditorapp.model.Definition
import com.cmps312.learningpackageeditorapp.model.LearningPackage
import com.cmps312.learningpackageeditorapp.model.Sentence
import com.cmps312.learningpackageeditorapp.model.Word
import com.cmps312.learningpackageeditorapp.repository.Repository

class LearningPackagesViewModel(appContext: Application) : AndroidViewModel(appContext) {

    private val _learningPackages = liveData {
        emit(Repository.getLearningPackages(appContext))
    } as MutableLiveData
    val learningPackages = _learningPackages as LiveData<List<LearningPackage>>
    // The above is the proper way of doing such a thing

    private var _words = MutableLiveData<List<Word>>()
    private var _sentences = MutableLiveData<List<Sentence>>()
    private var _definitions = MutableLiveData<List<Definition>>()

    lateinit var currentPackage: LearningPackage
    lateinit var currentWord: Word

    init {
        //_learningPackages.postValue(Repository.getLearningPackages(appContext) as ArrayList<LearningPackage>)
        _words.postValue(mutableListOf())
        _sentences.postValue(mutableListOf())
        _definitions.postValue(mutableListOf())
    }

    val words: LiveData<List<Word>> = _words

    fun addSentence(sentence: Sentence) {
        _sentences.value?.let {
            _sentences.value = it + sentence
        }
    }

    fun deleteSentence(sentence: Sentence) {
        _sentences.value?.let {
            _sentences.value = it - sentence
        }
    }

    fun addDefinition(definition: Definition) {
        _definitions.value?.let {
            _definitions.value = it + definition
        }
    }

    fun deleteDefinition(definition: Definition) {
        _definitions.value?.let {
            _definitions.value = it - definition
        }
    }

    fun addWord(wordName: String) {
        val newWord = Word(
            wordName,
            _definitions.value as ArrayList<Definition>,
            _sentences.value as ArrayList<Sentence>
        )
        _words.value?.let {
            _words.value = it + newWord
        }

        _sentences.value = ArrayList()
        _definitions.value = ArrayList()
    }

    fun addWord(word: Word) {
        _words.value?.let {
            _words.value = it + word
        }
    }

    fun clearWords() {
        _words.value?.filter { false }
    }

    fun deleteWord(word: Word) {
        _words.value?.let {
            _words.value = it - word
        }
    }

    fun addLearningPackage() {
        currentPackage.words = _words.value as List<Word>
        _learningPackages.value?.let { it ->
            // If item exists override it
            val foundAt = it.indexOfFirst { lP -> lP.packageID == currentPackage.packageID }
            if (foundAt >= 0) {
                it[foundAt] = currentPackage
                // This is needed to notify the observers
                _learningPackages.value = it
            } else {
                // This is needed to notify the observers
                it.add(0, currentPackage)
            }
        }
        // Clear Words RV
        _words.value = listOf()
    }

    fun deleteLearningPackage(learningPackage: LearningPackage) {
        _learningPackages.value?.remove(learningPackage)
    }

}