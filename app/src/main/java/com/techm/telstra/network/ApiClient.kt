package com.techm.telstra.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class for creating Retrofit instance
 */
object ApiClient {


    private const val API_BASE_URL = "https://dl.dropboxusercontent.com"

    private var servicesApiInterface: APIInterface? = null

    /**
     * This method is initialize for retrofit object
     * @return APIInterface instance
     */
    fun build(): APIInterface? {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            APIInterface::class.java
        )

        return servicesApiInterface as APIInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}