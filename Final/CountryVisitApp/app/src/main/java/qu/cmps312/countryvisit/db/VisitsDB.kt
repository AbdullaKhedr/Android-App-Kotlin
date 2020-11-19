package qu.cmps312.countryvisit.db

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.countryvisit.model.Continent
import qu.cmps312.countryvisit.model.Country
import qu.cmps312.countryvisit.model.Visit

@Database(
    entities = [Visit::class, Continent::class, Country::class],
    version = 1,
    exportSchema = false
)
abstract class VisitsDB : RoomDatabase() {
    abstract fun getVisitsDao(): VisitsDao
    abstract fun getContinentsDao(): ContinentsDao
    abstract fun getCountriesDao(): CountriesDao

    companion object {
        @Volatile
        private var database: VisitsDB? = null
        private const val DB_NAME = "Visits.db"

        @Synchronized
        fun getDatabase(context: Context): VisitsDB {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    VisitsDB::class.java,
                    DB_NAME
                ).build()
            }
            GlobalScope.launch {
                initDB(database!!, context)
            }
            return database as VisitsDB
        }

        suspend fun initDB(visitsDB: VisitsDB, context: Context) {
            val continentsDao = visitsDB.getContinentsDao()
            val countriesDao = visitsDB.getCountriesDao()

            if (continentsDao.getContinentsCount() == 0 && countriesDao.getCountriesCount() == 0) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                // Read from json files and write to db

                // 1. Insert continents
                var data = context.assets.open("continents.json")
                    .bufferedReader().use { it.readText() }
                val continents = json.decodeFromString<List<Continent>>(data)
                continentsDao.insertContinents(continents)

                // 2. Insert countries
                data = context.assets.open("countries.json")
                    .bufferedReader().use { it.readText() }
                val countries = json.decodeFromString<List<Country>>(data)
                countriesDao.insertCountries(countries)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Your DataBase is already initialized",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}