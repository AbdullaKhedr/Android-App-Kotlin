package qu.cmps312.lingosnacks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import qu.cmps312.lingosnacks.model.*
import qu.cmps312.lingosnacks.repositories.PackageRepository

class PackageViewModel(application: Application) : AndroidViewModel(application) {
    private val packageRepository = PackageRepository(application)
    private var _packages = MutableLiveData<MutableList<LearningPackage>>()
    var selectedPackage: LearningPackage? = null

    val packages = _packages as LiveData<List<LearningPackage>>

    init {
        // Init packages
        getPackages()
        addPackagesListener()
    }

    private fun addPackagesListener() {
        val query = packageRepository.getPackagesQuery()
        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println(">> Debug: List Update Listener failed. ${e.message}")
                return@addSnapshotListener
            }
            _packages.value = snapshot?.toObjects(LearningPackage::class.java)
        }
    }

    fun getPackages() = viewModelScope.launch {
        _packages.value = packageRepository.getPackages().toMutableList()
    }

    fun getPackages(searchText: String) = viewModelScope.launch {
        _packages.value = packageRepository.getPackages(searchText).toMutableList()
    }

    fun deleteOnlinePackage() {
        packageRepository.deleteOnlinePackage(selectedPackage!!)
        _packages.value?.let {
            it.remove(selectedPackage!!)
            _packages.value = it
            selectedPackage = null
        }
    }

    fun deleteLocalPackage() = viewModelScope.launch {
        packageRepository.deleteLocalPackage(selectedPackage!!)
        _packages.value?.let {
            it.remove(selectedPackage!!)
            _packages.value = it
            selectedPackage = null
        }
    }

    // Add package if exists otherwise add it
    fun upsertPackage(learningPackage: LearningPackage) {
        _packages.value?.let {
            // If item exists just increase the quantity
            var foundAt = it.indexOfFirst { pack -> pack.packageId == learningPackage.packageId }
            if (foundAt >= 0) {
                packageRepository.updatePackage(learningPackage)
                it[foundAt] = learningPackage
                // This is needed to notify the observers
                _packages.value = it
            } else {
                packageRepository.addPackage(learningPackage)
                // This is needed to notify the observers
                it.add(learningPackage)
                _packages.value = it
                println(">> Debug: upsertPackage: ${it.size}")
            }
        }
    }

    fun downloadPackage() = viewModelScope.launch {
        packageRepository.downloadPackage(selectedPackage?.packageId!!)
    }

    fun getRatings() = packageRepository.getRatings(selectedPackage?.packageId!!)

    fun addRating(rating: Rating) = packageRepository.addRating(rating)

    suspend fun getScores(uid: String) = packageRepository.getScores(uid)

    fun addScore(score: Score) = packageRepository.addScore(score)

    suspend fun getLeaderBoard() = packageRepository.getLeaderBoard()

    fun initFirestoreDB() = viewModelScope.launch {
        packageRepository.initFirestoreDB()
    }
}