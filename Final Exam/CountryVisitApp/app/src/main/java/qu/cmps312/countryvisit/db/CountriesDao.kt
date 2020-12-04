package qu.cmps312.countryvisit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import qu.cmps312.countryvisit.model.Country

@Dao
interface CountriesDao {

    @Query("select * from Country")
    suspend fun getCountries(): List<Country>

    @Query("select * from Country where continent=:continentName")
    suspend fun getCountriesByContinent(continentName: String): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(Countries: List<Country>): List<Long>

    @Query("select count(*) from Country")
    suspend fun getCountriesCount(): Int
}