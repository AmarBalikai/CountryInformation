package com.example.countryinformation.network

import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

/**
 * Used this class for writing all API methods
 */
interface APIInterface
{
    /**
     * This method is getting for list's of objects from server
     */
    @GET(Constant.countryUrl)
    fun getList(): Call<CountryModel>
}