package com.cmps312.countryvisitsapp.repository

import android.content.Context
import com.cmps312.countryvisitsapp.model.Country
import com.cmps312.countryvisitsapp.model.VisitedCountry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Repository {

    var visitedCountries = mutableListOf<VisitedCountry>()
    var countries = mutableListOf<Country>()

    fun getVisitedCountries(context: Context): MutableList<VisitedCountry> {
        val visitedCountryString = context.assets
            .open("visits.json")
            .bufferedReader()
            .use { it.readText() }
        visitedCountries = Json { ignoreUnknownKeys = true }.decodeFromString(visitedCountryString)
        return visitedCountries
    }

    fun getCountries(context: Context): MutableList<Country> {
        val countryString = context.assets
            .open("eu-countries.json")
            .bufferedReader()
            .use { it.readText() }
        countries = Json { ignoreUnknownKeys = true }.decodeFromString(countryString)
        return countries
    }

}