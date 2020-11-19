package qu.cmps312.countryvisit.db

import androidx.room.*
import qu.cmps312.countryvisit.model.Visit

@Dao
interface VisitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisits(visits: List<Visit>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisit(visit: Visit): Long

    //@Query("select count(*) from Visit")
    suspend fun getVisitsCount(): Int

    @Delete
    suspend fun deleteVisit(visit: Visit): Long

    @Update
    suspend fun updateVisit(visit: Visit): Long

}