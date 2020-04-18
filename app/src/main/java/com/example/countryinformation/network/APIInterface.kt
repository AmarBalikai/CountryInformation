package com.example.countryinformation.network

import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.utils.Constentes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface
{
    @GET(Constentes.getList)
    fun getList(): Call<CountryModel>
}