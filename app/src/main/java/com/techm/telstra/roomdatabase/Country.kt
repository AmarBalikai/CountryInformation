package com.techm.telstra.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * This data class for creating a room table
 */
@Entity(tableName = "country_table")
data class CountryEntity(@PrimaryKey(autoGenerate = true) var id:Int,var title:String,var description:String,var imageHref:String)