package cmps312.lab.covidtracker

import android.content.Context
import cmps312.lab.covidtracker.CovidStatRepository.covidStat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object CovidStatRepository {

    var covidStat = arrayListOf<CovidStat>()

    fun initCovidStat(context: Context) {
        // read from the file as a string
        val stadiumsJson = context.assets
            .open("covid-data.json")
            .bufferedReader()
            .use { it.readText() }
        // convert the string into list of objects
        covidStat = Json.decodeFromString(stadiumsJson)
    }

}

///////////////////////////////////////////////////////// functions done from lab3 ///////////////////////////////////////////////////////////
fun totalDeathsInQatar() {
    val totalInQatar = covidStat.find { it.country == "Qatar" }?.totalDeaths
    println("Total COVID-19 deaths in Qatar: $totalInQatar")
}

fun worldTotalDeaths() {
    var totalDethsWorld = 0
    covidStat.forEach { if (it.totalDeaths != null) totalDethsWorld += it.totalDeaths!! }
    println("Total COVID-19 deaths around the world: $totalDethsWorld")
}

fun activeCasesAsiaContinent() {
    var totalActiveAsia = 0
    covidStat.forEach {
        if (it.continent == "Asia")
            totalActiveAsia += it.totalCases!!
    }
    println("Total active cases in asia continent: $totalActiveAsia")
}

fun topFiveCountriesCases() {
    val sortedList = covidStat.sortedByDescending { it.totalCases }
    println("Top five countries with the highest number of COVID cases: \n")
    println("1st country: ${sortedList[0].country} with total = ${sortedList[0].totalCases}")
    println("2st country: ${sortedList[1].country} with total = ${sortedList[1].totalCases}")
    println("3th country: ${sortedList[2].country} with total = ${sortedList[2].totalCases}")
    println("4th country: ${sortedList[3].country} with total = ${sortedList[3].totalCases}")
    println("5th country: ${sortedList[4].country} with total = ${sortedList[4].totalCases}")
}

fun lowestFiveCountriesCases() {
    val sortedList = covidStat.sortedBy { it.totalCases }
    println("Top five countries with the lowest number of COVID cases: \n")
    println("1st country: ${sortedList[0].country} with total = ${sortedList[0].totalCases}")
    println("2st country: ${sortedList[1].country} with total = ${sortedList[1].totalCases}")
    println("3th country: ${sortedList[2].country} with total = ${sortedList[2].totalCases}")
    println("4th country: ${sortedList[3].country} with total = ${sortedList[3].totalCases}")
    println("5th country: ${sortedList[4].country} with total = ${sortedList[4].totalCases}")
}

fun totalCriticalCasesNearACountry(country: String) {
    var countryRegon: String? = ""
    covidStat.forEach {
        if (it.country == country)
            countryRegon = it.region.toString()
    }

    val NearCountres = covidStat.filter { it.region == countryRegon }

    var totalCriticalCasesNearACountry = 0
    NearCountres.forEach {
        if (it.criticalCases != null)
            totalCriticalCasesNearACountry += it.criticalCases!!
    }

    println("Total critical cases for the neighboring countries of $country: $totalCriticalCasesNearACountry")
    val sortedByPopulation = NearCountres.sortedBy { it.population }

    println("\nAnd those countries are: ")
    sortedByPopulation.forEach { println(it.country) }
}

fun topThreeRecoveryRegionsInAContinent(continent: String) {
    val regionsInAContinent = covidStat.filter { it.continent == continent }
    val top3 = regionsInAContinent.sortedByDescending { it.totalRecovered }
    println("Top three regions with highest recovery in $continent are: ")
    println("1. ${top3[0].country}")
    println("2. ${top3[1].country}")
    println("3. ${top3[2].country}")
}

fun lowestDeathInAContinent(continent: String) {
    val countriesInContinent = covidStat.filter { it.continent == continent }
    if (countriesInContinent.isNotEmpty()) {
        countriesInContinent.sortedBy { it.totalDeaths }
        println("The country with the lowest death in $continent is: ${countriesInContinent[0].country}")
    } else {
        println("No countries with Continent $continent")
    }
}



