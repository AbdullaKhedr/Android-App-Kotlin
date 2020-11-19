package qu.cmps312.countryvisit.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.countryvisit.model.Continent
import qu.cmps312.countryvisit.model.Visit
import qu.cmps312.countryvisit.repository.Repository

class VisitsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    val continents = repository.getContinents()
    var selectedContinent = MutableLiveData<Continent>()

    val countriesList = selectedContinent.switchMap {
        liveData {
            emit(repository.getCountriesByContinent(it.name))
        }
    }

    val visitsList = repository.getVisits()

    var isEdit = false
    var currentVisit = Visit()

    fun addVisit() = viewModelScope.launch(Dispatchers.IO) {
        repository.insertVisit(currentVisit)
    }

    fun deleteVisit() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteVisit(currentVisit)
    }

    fun updateVisit() = viewModelScope.launch(Dispatchers.IO) {
        repository.updateVisit(currentVisit)
        isEdit = false
    }
}