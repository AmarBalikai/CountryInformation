package com.techm.telstra.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This class is creating for room database
 */
@Database(entities = [CountryEntity::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun getCountryDao(): CountryDao

    companion object {
        @Volatile
        private var instance: CountryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            CountryDatabase::class.java, "country_database.db"
        )
            .fallbackToDestructiveMigration()//we need it when we increase version number
            .build()
    }
}