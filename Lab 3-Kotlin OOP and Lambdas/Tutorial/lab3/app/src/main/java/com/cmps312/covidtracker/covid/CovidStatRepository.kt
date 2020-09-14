package com.cmps312.covidtracker.covid

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object CovidStatRepository {

    var covidStats = arrayListOf<CovidStat>()

    init {
        // read from the file, convert to the list
        val data = File("data/covid-data.json").readText()
        covidStats = Json.decodeFromString(data)
    }
}

fun main() {
    CovidStatRepository.covidStats.forEach{it.id}
}
