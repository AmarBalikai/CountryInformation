package com.techm.telstra.viewmodel.repositoty

import android.app.Application


import androidx.lifecycle.MutableLiveData
import com.techm.telstra.network.ApiClient
import com.techm.telstra.model.CountryModel
import com.techm.telstra.model.InfoModelData
import com.techm.telstra.roomdatabase.CountryDao
import com.techm.telstra.roomdatabase.CountryDatabase
import com.techm.telstra.utils.Constant
import com.techm.telstra.utils.LocalSharedPreferences
import com.techm.telstra.viewmodel.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * This class for calling API
 * @param application for create room database object
 * */
class RepositoryViewModel(application: Application) {
    private var countryDao: CountryDao
    var mLocalSharedPreferences: LocalSharedPreferences = LocalSharedPreferences()
    private var countryDatabase: CountryDatabase = CountryDatabase.invoke(application)

    init {
        countryDao = countryDatabase.getCountryDao()
    }

    /**
     * This method for getting list of objects from the server
     * @param objCallback for get response to viewmodel
     */
    fun retrieveCountryFeaturesData(objCallback: ResponseCallback) {
        val listResponse: MutableLiveData<ArrayList<InfoModelData>> = MutableLiveData()

        val data: Call<CountryModel>? = ApiClient.build()?.getList()
        val enqueue = data?.enqueue(object : Callback<CountryModel> {
            override fun onResponse(call: Call<CountryModel>, response: Response<CountryModel>) {
                if (response.isSuccessful) {

                    mLocalSharedPreferences.putString(Constant.countryName,response.body()?.title)
                    listResponse.value = response.body()?.rows
                    /**
                     * Send success response to viewModel using this callback
                     */
                    objCallback.onSuccess(listResponse)
                }
            }
            override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                /**
                 * Send failure response to viewModel
                 */
                objCallback.onError(t.message)
            }
        })
    }
}