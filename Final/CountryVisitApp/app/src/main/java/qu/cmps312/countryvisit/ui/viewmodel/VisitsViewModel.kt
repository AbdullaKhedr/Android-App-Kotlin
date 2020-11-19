package qu.cmps312.countryvisit.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.countryvisit.model.Visit
import qu.cmps312.countryvisit.repository.Repository

class VisitsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    val continents = repository.getList1()
    var selectedContinent = MutableLiveData<String>()

    val countriesList = selectedContinent.switchMap {
        liveData {
            emit(repository.getCountries())
        }
    }

    val visitsList = selectedContinent.switchMap {
        liveData {
            emit(repository.getVisits())
        }
    }

    var isEdit = false
    var currentVisit = Visit()

    fun addVisit() = viewModelScope.launch(Dispatchers.IO) {

    }

    fun deleteVisit() = viewModelScope.launch(Dispatchers.IO) {

    }

    fun updateVisit() = viewModelScope.launch(Dispatchers.IO) {
        isEdit = false
    }
}