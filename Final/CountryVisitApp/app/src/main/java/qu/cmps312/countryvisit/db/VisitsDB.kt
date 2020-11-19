package qu.cmps312.countryvisit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.countryvisit.model.Visit

//@Database(entities = [Visit::class], version = 1, exportSchema = false)
abstract class VisitsDB : RoomDatabase() {
    abstract fun getVisitsDao(): VisitsDao

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

        private suspend fun initDB(visitsDB: VisitsDB, context: Context) {
            val visitsDao = visitsDB.getVisitsDao()
            val visitsCount = visitsDao.getVisitsCount()

            // If visitsCount = 0 then means the DB is empty
            if (visitsCount == 0) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                // Read from json files and write to db

                // 1. Insert Visits
                var data = context.assets.open("visits.json")
                    .bufferedReader().use { it.readText() }
                val visits = json.decodeFromString<List<Visit>>(data)
                val visitsIds = visitsDao.insertVisits(visits)
                println(">> Debug: visitsIds = visitsDao.insertVisits(visits) $visitsIds")

//                // 2. Insert ratings
//                data = context.assets.open("ratings.json")
//                    .bufferedReader().use { it.readText() }
//                var ratings = json.decodeFromString<List<Rating>>(data)
//                println(">> Debug: initDB ratings $ratings")
//                ratings = ratings.map {
//                    // Lookup the category id
//                    val category = packageDao.getLearningPackage(it.packageId!!)
//                    Rating()
//                }
//              val productIds = packageDao.insertProducts(products)
//              println(">> Debug: productIds = productDao.insertProducts(products) $productIds")
            }
        }
    }

}