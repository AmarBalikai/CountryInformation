package com.example.countryinformation.viewmodel.repositoty

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.network.ApiClient
import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.roomdatabase.CountryDao
import com.example.countryinformation.roomdatabase.CountryDatabase
import com.example.countryinformation.roomdatabase.CountryEntity
import com.example.countryinformation.utils.Constant
import com.example.countryinformation.utils.LocalSharedPreferences
import com.example.countryinformation.viewmodel.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(application: Application) {

    private var countryDao: CountryDao
    private lateinit var listCountry: LiveData<List<CountryEntity>>

    var mlocalSharedPreferences: LocalSharedPreferences = LocalSharedPreferences()
    private lateinit var countryDatabase: CountryDatabase

    init {
        countryDatabase = CountryDatabase.invoke(application)
        countryDao = countryDatabase.getCountryDao()
    }


    fun getDataFromServer(objCallback: ResponseCallback) {
        val listResponse: MutableLiveData<ArrayList<InfoModelData>> = MutableLiveData()

        val data: Call<CountryModel>? = ApiClient.build()?.getList()
        val enqueue = data?.enqueue(object : Callback<CountryModel> {
            override fun onResponse(call: Call<CountryModel>, response: Response<CountryModel>) {
                if (response.isSuccessful) {

                    mlocalSharedPreferences.putString(Constant.countryName,response.body()?.title)
                    listResponse.value = response.body()?.rows
                    objCallback.onSuccess(listResponse)


                }
            }
            override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                objCallback.onError(t.message)
               // listResponse=null
            }
        })
    }

}