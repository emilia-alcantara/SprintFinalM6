package cl.individual.sprintfinalm6.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhoneAPI {

    // Request para obtener la lista de productos
    @GET("products/")
    suspend fun getPhoneData(): Response<List<Phone>>

    // Request para obtener 1 producto específico según el id
    @GET("details/{id}")
    suspend fun getPhoneDetails(@Path("id") id:Int) : Response<PhoneDetails>

}