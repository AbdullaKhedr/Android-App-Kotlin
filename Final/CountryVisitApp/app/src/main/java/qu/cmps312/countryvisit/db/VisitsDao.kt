package qu.cmps312.countryvisit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.selects.select
import qu.cmps312.countryvisit.model.Visit

@Dao
interface VisitsDao {

    @Query("select * from Visit")
    fun getVisits(): LiveData<List<Visit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisits(visits: List<Visit>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisit(visit: Visit): Long

    @Query("select count(*) from Visit")
    suspend fun getVisitsCount(): Int

    @Delete
    suspend fun deleteVisit(visit: Visit): Int

    @Update
    suspend fun updateVisit(visit: Visit): Int

}