package com.cmps312.covidtracker.covid

import kotlinx.serialization.Serializable

@Serializable
data class CountryFlag(val code: String, val name: String)