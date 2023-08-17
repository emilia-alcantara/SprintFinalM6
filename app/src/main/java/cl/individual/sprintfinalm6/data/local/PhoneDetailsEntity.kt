package cl.individual.sprintfinalm6.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Set up entidad que almacenará los detalles en la database local
@Entity(tableName = "phone_detail_table")
data class PhoneDetailsEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val price: Int,
    val image: String,
    val description: String,
    @ColumnInfo("last_price") val lastPrice: Int,
    val credit: Boolean)