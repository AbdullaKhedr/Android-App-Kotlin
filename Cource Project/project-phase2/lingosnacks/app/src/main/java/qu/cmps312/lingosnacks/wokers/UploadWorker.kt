package qu.cmps312.lingosnacks.wokers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.lingosnacks.model.Score
import qu.cmps312.lingosnacks.repositories.PackageRepository

const val KEY_SCORE_ARG = "SCORE"

class UploadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val appContext = context
    override fun doWork(): Result {
        return try {
            val packagesRepository = PackageRepository(appContext)
            val score: Score = Json.decodeFromString(inputData.getString(KEY_SCORE_ARG)!!)
            packagesRepository.addScoreByWorker(score)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}