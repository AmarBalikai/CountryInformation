package com.techm.telstra.network

import com.techm.telstra.model.CountryModel
import com.techm.telstra.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

/**
 * Used this class for writing all API methods
 */
interface APIInterface {
    /**
     * This method is getting for list's of objects from server
     */
    @GET(Constant.countryUrl)
    fun getList(): Call<CountryModel>
}