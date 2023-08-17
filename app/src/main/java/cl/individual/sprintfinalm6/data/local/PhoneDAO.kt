package cl.individual.sprintfinalm6.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhoneDAO {

    // Métodos para insertar los datos desde la api a la database local
    //(Será usado/llamado por el repositorio)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoneList(phoneList: List<PhoneEntity>)

    // Query para obtener la data completa almacenada en la database local
    @Query("SELECT * FROM phone_table ORDER BY id ASC")
    fun getPhoneData(): LiveData<List<PhoneEntity>>

    // Métodos para insertar los datos desde la api a la database local
    //(Será usado/llamado por el repositorio)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoneDetails(phoneDetailsEntity: PhoneDetailsEntity)

    // Query para obtener solo 1 entity según el id del item seleccionado
    @Query("SELECT * FROM phone_detail_table WHERE id LIKE :id")
    fun getPhoneDetails(id: Int): LiveData<PhoneDetailsEntity>

}