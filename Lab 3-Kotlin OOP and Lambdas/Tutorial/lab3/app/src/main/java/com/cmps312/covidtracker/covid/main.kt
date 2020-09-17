package com.cmps312.covidtracker.covid

fun main() {
    CovidStatRepository.totalDeathsInQatar()
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.worldTotalDeaths()
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.activeCasesAsiaContinent()
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.topFiveCountriesCases()
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.lowestFiveCountriesCases()
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.totalCriticalCasesNearACountry("Qatar")
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.topThreeRecoveryRegionsInAContinent("Asia")
    println("--------------------------------------------------------------------------------------------")
    CovidStatRepository.lowestDeathInAContinent("Asia")
}
