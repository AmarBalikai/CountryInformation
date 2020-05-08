package com.techm.telstra.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query

/**
 * In this interface have all queries fo room database
 */
@Dao
interface CountryDao {
    @Query("SELECT * FROM country_table")
    fun getAllCountries(): LiveData<List<CountryEntity>>

    @Insert
    fun insertAll(countries: ArrayList<CountryEntity>?)

    @Query("DELETE FROM country_table")
     fun deleteAllUsers()
}