package cl.individual.sprintfinalm6.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Set up entidad en la database local
@Entity(tableName= "phone_table")
data class PhoneEntity (
    @PrimaryKey val id: Int,
    val name:String,
    val price: Int,
    val image:String)