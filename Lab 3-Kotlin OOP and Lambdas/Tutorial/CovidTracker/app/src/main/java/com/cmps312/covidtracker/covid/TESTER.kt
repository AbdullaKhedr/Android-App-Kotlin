package com.cmps312.covidtracker.covid

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

fun main() {

    var covidStats = arrayListOf<CovidStat>()
    var flags = arrayListOf<CountryFlag>()
    val sorted = mutableListOf<CovidStat1>()


    val data = File("data/covid-data.json").readText()
    covidStats = Json { ignoreUnknownKeys = true }.decodeFromString(data)

    val data1 = File("data/flags.json").readText()
    flags = Json { ignoreUnknownKeys = true }.decodeFromString(data1)


    var dummy: CovidStat1 = CovidStat1(
        999, "", "", "", 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, "999"
    )

    var done = false

    covidStats.forEach { x ->
        flags.forEach { y ->
            if (x.country == y.name) {
                var dummy1: CovidStat1 = CovidStat1(
                    x.id,
                    x.country,
                    x.continent,
                    x.region,
                    x.totalCases,
                    x.newCases,
                    x.totalDeaths,
                    x.newDeaths,
                    x.totalRecovered,
                    x.newRecovered,
                    x.activeCases,
                    x.criticalCases,
                    x.casesPer1M,
                    x.deathsPer1M,
                    x.totalTests,
                    x.testsPer1M,
                    x.population,
                    y.code
                )
                sorted.add(dummy1)
                done = true

            }
        }
        if (done == false)
            sorted.add(dummy)
    }

    println(covidStats.size)
    println(sorted.size)

    val json = Json.encodeToJsonElement(sorted).toString()
    File("covid-data11.json").bufferedWriter().use { out ->
        out.write(json)
    }
}