package qu.cmps312.countryvisit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import qu.cmps312.countryvisit.model.Continent
import qu.cmps312.countryvisit.model.Country
import qu.cmps312.countryvisit.model.Visit

@Dao
interface ContinentsDao {

    @Query("select * from Continent")
    fun getContinents(): LiveData<List<Continent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContinent(continent: Continent): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContinents(continents: List<Continent>): List<Long>

    @Query("select count(*) from Continent")
    suspend fun getContinentsCount(): Int
}