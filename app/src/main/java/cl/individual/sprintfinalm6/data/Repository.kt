package cl.individual.sprintfinalm6.data

import android.util.Log
import androidx.lifecycle.LiveData
import cl.individual.sprintfinalm6.data.local.PhoneDAO
import cl.individual.sprintfinalm6.data.local.PhoneDetailsEntity
import cl.individual.sprintfinalm6.data.local.PhoneEntity
import cl.individual.sprintfinalm6.data.remote.PhoneAPI

class Repository(private val phoneAPI: PhoneAPI, private val phoneDAO: PhoneDAO) {


    suspend fun loadPhonesToDatabase() {
        val response = phoneAPI.getPhoneData()
        if (response.isSuccessful) {
            val phoneList = response.body()
            phoneList?.let { phones ->
                val phoneEntity = phones.map {
                    it.transformToPhoneEntity()
                }
                phoneDAO.insertPhoneList(phoneEntity)
            }
        } else {
            Log.d("LOAD PHONES TO DATABASE", "**** ERROR ****")
        }
    }

    suspend fun loadPhoneDetailsToDatabase(id: Int) {
        try {
            val response = phoneAPI.getPhoneDetails(id)
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.let { phoneDetails ->
                    val phoneDetailsEntity = phoneDetails.transformToPhoneDetailsEntity(id)
                    phoneDAO.insertPhoneDetails(phoneDetailsEntity)
                }
            } else {
                Log.d("LOAD PHONE DETAILS TO DATABASE", "**** ERROR ****")
            }
        } catch (e: Exception) {
            Log.d("LOAD PHONE DETAILS", "**** ERROR LOADING ****", e)
        }
    }

    fun getPhonesFromLocal(): LiveData<List<PhoneEntity>> = phoneDAO.getPhoneData()

    fun getPhoneDetailsFromLocal(id: Int): LiveData<PhoneDetailsEntity> =
        phoneDAO.getPhoneDetails(id)

}