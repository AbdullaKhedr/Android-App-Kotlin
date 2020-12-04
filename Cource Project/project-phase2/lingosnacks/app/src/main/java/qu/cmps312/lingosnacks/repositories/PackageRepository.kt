package qu.cmps312.lingosnacks.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.work.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import qu.cmps312.lingosnacks.db.PackagesDB
import qu.cmps312.lingosnacks.model.*
import qu.cmps312.lingosnacks.wokers.KEY_SCORE_ARG
import qu.cmps312.lingosnacks.wokers.UploadWorker
import java.io.File
import java.util.*


fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

data class ScoreSummary(val gameName: String, val score: Int, val outOf: Int, val uid: String) {
    override fun toString(): String {
        val scorePercentage = (score.toDouble() / outOf * 100.0).round(2)
        return "$gameName -> $score / $outOf ($scorePercentage%)"
    }
}

data class LeaderBoardMember(
        val rank: Int, val uid: String, val displayName: String,
        val photoUri: String,
        val gameName: String, val score: Int, val outOf: Int
)

class PackageRepository(val context: Context) {
    // ToDo: Implement all PackageRepository methods to read/write from the online/local database

    private val packagesCollectionRef by lazy {
        Firebase.firestore.collection("packages")
    }

    private val usersCollectionRef by lazy {
        Firebase.firestore.collection("users")
    }

    private val scoresCollectionRef by lazy {
        Firebase.firestore.collection("scores")
    }

    private val ratingsCollectionRef by lazy {
        Firebase.firestore.collection("ratings")
    }

    private val storageRef by lazy {
        Firebase.storage.reference
    }

    private val packagesDao by lazy {
        PackagesDB.getDatabase(context).packagesDao()
    }

    val scoresDao by lazy {
        PackagesDB.getDatabase(context).scoresDao()
    }

    // Start of Functions:
    suspend fun getPackages(): List<LearningPackage> {
        return packagesCollectionRef.get().await().toObjects(LearningPackage::class.java)
    }

    fun getPackagesQuery(): Query = packagesCollectionRef

    suspend fun getPackages(searchText: String): List<LearningPackage> {
        val packages = getPackages()
        return if (searchText.isEmpty())
            packages
        else
            packages.filter {
                it.keywords.contains(searchText, true) ||
                        it.description.contains(searchText, true)
            }
    }

    // ToDo: Add/update the learning package to Firestore & upload the associated media files to Firebase Cloud Storage
    fun addPackage(learningPackage: LearningPackage) {
        uploadMedia(learningPackage)
        packagesCollectionRef.add(learningPackage)
    }

    fun updatePackage(learningPackage: LearningPackage) {
        uploadMedia(learningPackage)
        packagesCollectionRef.document(learningPackage.packageId).set(learningPackage)
    }

    private fun uploadMedia(learningPackage: LearningPackage) {
        learningPackage.words.forEach {
            it.sentences.forEach { sentence ->
                sentence.resources.forEach { rsc ->
                    if (rsc.type == ResourceTypeEnum.Website || rsc.url.contains("onFireStore-") || rsc.url.contains("http"))
                        return@forEach

                    val file = Uri.parse(rsc.url)
                    val fileName = "onFireStore-" + UUID.randomUUID().toString() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(file))
                    rsc.url = fileName
                    val resource = storageRef.child(fileName)
                    resource.putFile(file)
                }
            }
        }
    }

    fun deleteMedia(resource: Resource) {
        if(resource.url.isEmpty())
            return

        storageRef.child(resource.url).delete()

        val dir = context.getDir("media", Context.MODE_PRIVATE)
        val file = File(dir, resource.url)
        if (file.isFile)
            file.delete()
    }

    // ToDo: Delete package from Firestore and its associated resources from Cloud Storage
    fun deleteOnlinePackage(learningPackage: LearningPackage) {
        packagesCollectionRef.document(learningPackage.packageId).delete()
        deletePackageMedia(learningPackage)
    }

    private fun deletePackageMedia(learningPackage: LearningPackage) {
        learningPackage.words.forEach{
            it.sentences.forEach{sentence -> sentence.resources.forEach {rsc ->
                deleteMedia(rsc)
            } }
        }
    }

    private suspend fun getLocalPackage(packageId: String) =
            packagesDao.getLearningPackage(packageId)

    // ToDo: Download package from Firestore and its associated resources from Cloud Storage so that the package can be used offline
    suspend fun downloadPackage(packageId: String) {
        val localPackage = getLocalPackage(packageId)
        val onlinePackage = packagesCollectionRef.document(packageId)
                .get().await()
        //the package doesn't exist so add it
        Log.i("MYTAG", "This is the local package: $localPackage")
        if (localPackage == null) {
            onlinePackage.toObject(LearningPackage::class.java)
                ?.let { packagesDao.insertLearningPackage(it) }
            Toast.makeText(context, "Package added successfully!", Toast.LENGTH_SHORT).show()
        } else {
            //the package exist so we need to check the version
            val pack = onlinePackage.toObject(LearningPackage::class.java)
            if (localPackage.version < pack?.version!!) {
                packagesDao.insertLearningPackage(pack)

                Toast.makeText(context, "Package overwritten successfully!", Toast.LENGTH_SHORT)
                        .show()
            }
        }

        try {
            localPackage?.words?.forEach {
                it.sentences.forEach { sentence ->
                    sentence.resources.forEach { rsc ->
                        if(!rsc.url.contains("onFireStore-"))
                            return@forEach
                        val dir = context.getDir("media", Context.MODE_PRIVATE)
                        val localFile = File(dir, rsc.url)

                        if(!localFile.isFile)
                            storageRef.child(rsc.url).getFile(localFile)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    suspend fun fireBaseLoadFile(url: String): String {
        return storageRef.child(url).downloadUrl.await().toString()
    }

    // ToDo: Delete package from local database and its associated resource files
    suspend fun deleteLocalPackage(learningPackage: LearningPackage) {
        packagesDao.deleteLearningPackage(learningPackage)
        deletePackageMedia(learningPackage)
    }

    fun getRatings(packageId: String): List<Rating> {
        val data = context.assets.open("ratings.json")
                .bufferedReader().use { it.readText() }
        val ratings = Json { ignoreUnknownKeys = true }.decodeFromString<List<Rating>>(data)
        return ratings.filter { it.packageId == packageId }
    }

    // ToDO: Whenever a new rating is added then recompute & update the package avgRating and numRatings
    // this is typically done to improve performance and avoid computing the rating upon request
    /*
        // Compute new number of ratings
        val newNumRatings = numRatings + 1

        // Compute new average rating
        val oldRatingTotal = avgRating * numRatings
        val newAvgRating = (oldRatingTotal + rating) / newNumRatings
     */
    fun addRating(rating: Rating) {
        ratingsCollectionRef.add(rating)
        println(">> Debug: PackageRepository.addRating: $rating")
    }

    // ToDo: replace this example data with database query to get scores summary by uid (Done)
    suspend fun getScores(uid: String? = null): List<ScoreSummary> {
        val scores = if (uid != null)
            scoresCollectionRef.whereEqualTo("uid", uid).get().await().toObjects(Score::class.java)
        else
            scoresCollectionRef.get().await().toObjects(Score::class.java).sortedByDescending{it.score}
        return scores.map {ScoreSummary(it.gameName, it.score, it.outOf, it.uid)}
    }

    //we need to check for internet first, if there is no internet, we have to
    //save it in sql, then when the internet is available, we upload it to the fire store.
    fun addScore(score: Score) {
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val scoreData: Data = workDataOf(KEY_SCORE_ARG to Json.encodeToString(score))
        val request = OneTimeWorkRequestBuilder<UploadWorker>().setConstraints(constraints).setInputData(scoreData).build()
        workManager.enqueue(request)

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if(!isConnected) {
            GlobalScope.launch {
                scoresDao.insertScore(score)
            }
        }
    }

    fun addScoreByWorker(score: Score) {
        scoresCollectionRef.add(score)
        println(">> Debug: PackageRepository.addScore: $score")
    }

    // ToDo: replace this example data with database query to get leader board members
    suspend fun getLeaderBoard(): List<LeaderBoardMember> {
        val scores = getScores()
        var gameOneCount = 0
        var gameTwoCount = 0

        val allScores = scores.map {
            val user = usersCollectionRef.whereEqualTo("uid", it.uid).get().await().toObjects(User::class.java)
            if (it.gameName == "Unscramble Sentence")
                LeaderBoardMember(++gameOneCount, it.uid, "${user[0].firstName} ${user[0].lastName}", user[0].photoUri, it.gameName, it.score, it.outOf)
            else
                LeaderBoardMember(++gameTwoCount, it.uid, "${user[0].firstName} ${user[0].lastName}", user[0].photoUri, it.gameName, it.score, it.outOf)
        }

        val gameOne = allScores.filter{it.gameName == "Unscramble Sentence"}.take(3)
        val gameTwo = allScores.filter{it.gameName == "Match Definition"}.take(3)
        return gameOne + gameTwo
    }

    // ToDo initialize Firestore db with data from packages.json and users.json (Done)
    private suspend fun isTherePackagesAndUsersCollection(): Boolean {
        val packagesQueryResult = packagesCollectionRef.limit(1).get().await()
        val usersQueryResult = usersCollectionRef.limit(1).get().await()
        return packagesQueryResult.isEmpty && usersQueryResult.isEmpty
    }

    suspend fun initFirestoreDB() {
        if (!isTherePackagesAndUsersCollection()) {
            Toast.makeText(context, "Firebase database is already initialized", Toast.LENGTH_SHORT)
                    .show()
            return
        }

        val authRepo = AuthRepository(context)

        val usersData = context.assets.open("users.json")
                .bufferedReader().use { it.readText() }
        val users = Json.decodeFromString<List<User>>(usersData)

        val packagesData = context.assets.open("packages.json")
                .bufferedReader().use { it.readText() }
        val packages = Json.decodeFromString<List<LearningPackage>>(packagesData)

        val ratingsData = context.assets.open("ratings.json")
                .bufferedReader().use { it.readText() }
        val ratings =
                Json { ignoreUnknownKeys = true }.decodeFromString<List<Rating>>(ratingsData)

        users.forEach { authRepo.signUp(it) } //signIn will add to users collections as well
        packages.forEach { packagesCollectionRef.add(it) }
        ratings.forEach { ratingsCollectionRef.add(it) }
    }

}