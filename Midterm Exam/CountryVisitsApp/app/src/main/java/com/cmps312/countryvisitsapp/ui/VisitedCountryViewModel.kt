package com.cmps312.countryvisitsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cmps312.countryvisitsapp.model.VisitedCountry
import com.cmps312.countryvisitsapp.repository.Repository

class VisitedCountryViewModel(application: Application) : AndroidViewModel(application) {

    private var _visitedCountries = MutableLiveData<MutableList<VisitedCountry>>()
    lateinit var newVisitedCountry: VisitedCountry

    init {
        _visitedCountries.postValue(Repository.getVisitedCountries(application))
    }

    fun visitedCountries(): LiveData<MutableList<VisitedCountry>> = _visitedCountries

    fun addCountry() {
        _visitedCountries.value?.add(0, newVisitedCountry)
    }

}