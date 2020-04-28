package com.example.countryinformation.network

import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface
{
    @GET(Constant.countryUrl)
    fun getList(): Call<CountryModel>
}