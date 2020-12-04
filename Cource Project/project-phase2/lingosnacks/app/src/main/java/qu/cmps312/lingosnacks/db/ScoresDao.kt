package qu.cmps312.lingosnacks.db

import androidx.room.*
import qu.cmps312.lingosnacks.model.Score

@Dao
interface ScoresDao {

    @Query("select * from Score")
    suspend fun getScores(): List<Score>

    @Query("select * from Score where uid=:uid")
    suspend fun getScoresByUid(uid: String): List<Score>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: Score): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScores(score: List<Score>): List<Long>

    @Delete
    suspend fun deleteScore(score: Score)

    @Update
    suspend fun updateScore(score: Score)

}