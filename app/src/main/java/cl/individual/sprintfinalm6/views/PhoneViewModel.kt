package cl.individual.sprintfinalm6.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cl.individual.sprintfinalm6.data.Repository
import cl.individual.sprintfinalm6.data.local.PhoneDatabase
import cl.individual.sprintfinalm6.data.local.PhoneDetailsEntity
import cl.individual.sprintfinalm6.data.remote.PhoneRetrofit
import kotlinx.coroutines.launch

class PhoneViewModel(app: Application) : AndroidViewModel(app) {

    val repo: Repository

    init {
        val myAPI = PhoneRetrofit.getRetrofitPhone()
        val myDAO = PhoneDatabase.getDatabase(app).getPhoneDAO()

        repo = Repository(myAPI, myDAO)
    }

    fun getAllPhones() = viewModelScope.launch {
        repo.loadPhonesToDatabase()
    }

    fun getPhoneDetails(id: Int) = viewModelScope.launch {
        repo.loadPhoneDetailsToDatabase(id)
    }

    fun phonesLiveData() = repo.getPhonesFromLocal()

    fun phoneDetailsLiveData(id: Int): LiveData<PhoneDetailsEntity> =
        repo.getPhoneDetailsFromLocal(id)
}