package cl.individual.sprintfinalm6.views

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import cl.individual.sprintfinalm6.R
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

    fun sendEmail(context:Context, name: String, selectedPhoneId: Int) {

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            //putExtra(Intent.EXTRA_EMAIL, arrayOf("info@novaera.cl"))
            putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.send_email_to))
            putExtra(Intent.EXTRA_SUBJECT, "Consulta $name id $selectedPhoneId")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hola\n Vi el producto " + name + " de código " + selectedPhoneId +
                        "y me gustaría que me contactaran a este correo o al siguiente número ___. " +
                        "\n Quedo atento.")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }

    }
}