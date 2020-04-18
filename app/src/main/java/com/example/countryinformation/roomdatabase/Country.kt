package com.example.countryinformation.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class CountryEntity(@PrimaryKey(autoGenerate = true) var id:Int,var title:String,var description:String,var imageHref:String) {

}