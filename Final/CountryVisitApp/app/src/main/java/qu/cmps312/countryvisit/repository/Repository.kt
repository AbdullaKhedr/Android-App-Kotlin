package qu.cmps312.countryvisit.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.countryvisit.db.VisitsDB
import qu.cmps312.countryvisit.model.Country
import qu.cmps312.countryvisit.model.Visit

class Repository(private val context: Context) {

    // dummy getContinents() instead
    fun getList1(): LiveData<List<String>> {
        return liveData<List<String>> { emit(listOf<String>("A", "B", "C")) }
    }

    private val visitsDB by lazy {
        VisitsDB.getDatabase(context)
    }

    private val visitsDao by lazy {
        visitsDB.getVisitsDao()
    }

    private fun getJsonAsStringData(fileName: String): String {
        return context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }

    fun getContinents(): List<String> {
        val continentsString = getJsonAsStringData("continents.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(continentsString)
    }

    fun getCountries(): List<Country> {
        val countriesString = getJsonAsStringData("countries.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(countriesString)
    }

    fun getVisits(): List<Visit> {
        val visitsString = getJsonAsStringData("visits.json")
        return Json { ignoreUnknownKeys = true }.decodeFromString(visitsString)
    }

}