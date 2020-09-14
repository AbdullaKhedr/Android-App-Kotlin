package com.cmps312.covidtracker.covid

fun main() {
    println("Top 5 Country = ${CovidStatRepository.covidStats
        .map { "${it.country} -> ${it.totalCases}\n" }}")
}