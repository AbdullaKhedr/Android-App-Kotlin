package qu.cmps312.lingosnacks.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import qu.cmps312.lingosnacks.model.LearningPackage
import qu.cmps312.lingosnacks.model.Score
import qu.cmps312.lingosnacks.repositories.PackageRepository

@Database(entities = [LearningPackage::class, Score::class], version = 1, exportSchema = false)
@TypeConverters(WordListConverter::class)
abstract class PackagesDB : RoomDatabase() {
    abstract fun packagesDao(): PackagesDao
    abstract fun scoresDao(): ScoresDao

    companion object {
        @Volatile
        private var database: PackagesDB? = null
        private const val DB_NAME = "Packages.db"

        @Synchronized
        fun getDatabase(context: Context): PackagesDB {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    PackagesDB::class.java,
                    DB_NAME
                ).build()
            }
            Log.i("MYTAG", "getDatabase: we are in getDatabase now")
            /*GlobalScope.launch {
                initDB(database!!, context)
            }*/
            return database as PackagesDB
        }

        private suspend fun initDB(packagesDB: PackagesDB, context: Context) {
            val packageDao = packagesDB.packagesDao()
            val packagesCount = packageDao.getPackagesCount()
            val packagesRepository = PackageRepository(context)

            // If packagesCount = 0 then means the DB is empty
            if (packagesCount == 0) {
                val packages = packagesRepository.getPackages()
                packageDao.insertLearningPackages(packages)
                Log.i("MYTAG", "We are in initDatabase ")
                Log.i("MYTAG", "initDB: ${packages}")
//                var data = context.assets.open("packages.json")
//                    .bufferedReader().use { it.readText() }
//                val packages = json.decodeFromString<List<LearningPackage>>(data)
//                packages.forEach { it.packageId.toLong() }
//                val packagesIds = packageDao.insertLearningPackages(packages)
//
//                println(">> Debug: packagesIds = packageDao.insertLearningPackages(packages) $packagesIds")
//                Log.i("MYTAG", ">> initDB: $packages ")
//                // 2. Insert ratings
//                data = context.assets.open("ratings.json")
//                    .bufferedReader().use { it.readText() }
//                var ratings = json.decodeFromString<List<Rating>>(data)
//                println(">> Debug: initDB ratings $ratings")*/
//                /*ratings = ratings.map {
//                    // Lookup the package id
//                    val learningPackage = packageDao.getLearningPackage(it.packageId)
//                    Rating(learningPackage?.packageId.toString(),it.comment,it.doneOn,learningPackage!!.author,it.rating)
//                }
//                //ratings.forEach { it.ratingId.toLong()}
//                //packageDao.insertRatings(ratings)
//                val productIds = packageDao.insertProducts(products)
//                println(">> Debug: productIds = productDao.insertProducts(products) $productIds")
            }
        }
    }
}