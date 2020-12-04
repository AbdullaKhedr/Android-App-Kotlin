package qu.cmps312.lingosnacks.db

import androidx.room.*
import qu.cmps312.lingosnacks.model.LearningPackage

@Dao
interface PackagesDao {

    @Query("SELECT * FROM LearningPackage")
    suspend fun getLearningPackages(): List<LearningPackage>

    @Query("SELECT * FROM LearningPackage where packageId=:pid")
    suspend fun getLearningPackage(pid: String): LearningPackage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearningPackages(learningPackages: List<LearningPackage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearningPackage(learningPackage: LearningPackage): Long

    @Delete
    suspend fun deleteLearningPackage(learningPackage: LearningPackage)

    @Update
    suspend fun updateLearningPackage(learningPackage: LearningPackage)

    @Query("select count(*) from LearningPackage")
    suspend fun getPackagesCount(): Int

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRatings(Rating : List<Rating>)*/
}