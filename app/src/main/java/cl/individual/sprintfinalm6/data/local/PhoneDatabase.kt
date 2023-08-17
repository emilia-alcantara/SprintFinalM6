package cl.individual.sprintfinalm6.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PhoneEntity::class, PhoneDetailsEntity::class], version = 1)
abstract class PhoneDatabase : RoomDatabase() {
    abstract fun getPhoneDAO(): PhoneDAO

    companion object {
        @Volatile
        private var INSTANCE: PhoneDatabase? = null

        fun getDatabase(context: Context): PhoneDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneDatabase::class.java,
                    "phone_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}