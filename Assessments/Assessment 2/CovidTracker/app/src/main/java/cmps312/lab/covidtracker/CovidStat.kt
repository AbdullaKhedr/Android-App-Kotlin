package cmps312.lab.covidtracker

import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the 1st (Project) build.gradle:
    //Added for Kotlin Serialization
    classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"

2) Add to dependencies of the 2nd (Module) build.gradle
    //Added for Kotlin Serialization
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC"

3) Add this apply plugin to the 2nd build.gradle before line "android {"
    //Added for Kotlin Serialization
    apply plugin: 'kotlinx-serialization'
 */

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
    var population: Int?,
    var code: String
)

