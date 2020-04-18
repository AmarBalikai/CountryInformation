package com.example.countryinformation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.roomdatabase.CountryEntity

class CountryModel
{
    var title:String=""
    lateinit var rows: ArrayList<InfoModelData>
}