package com.cmps312.covidtracker.covid

import kotlinx.serialization.Serializable

@Serializable
data class CovidStat(
    var id: Int,
    var country: String?,
    var continent: String?,
    var region: String?,
    var totalCases: Int?,
    var newCases: Int?,
    var totalDeaths: Int?,
    var newDeaths: Int?,
    var totalRecovered: Int?,
    var newRecovered: Int?,
    var activeCases: Int?,
    var criticalCases: Int?,
    var casesPer1M: Int?,
    var deathsPer1M: Int?,
    var totalTests: Int?,
    var testsPer1M: Int?,
    var population: Int?)

