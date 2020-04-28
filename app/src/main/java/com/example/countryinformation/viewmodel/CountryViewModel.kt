package com.example.countryinformation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.model.ApiFailModel
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.utils.Constant
import com.example.countryinformation.viewmodel.repositoty.RepositoryViewModel
import org.jetbrains.annotations.NotNull

class CountryViewModel(@NotNull application: Application) : AndroidViewModel(application),ResponseCallback{
   private var objApplication: Application = application
    private lateinit var repositoryViewModel: RepositoryViewModel

    lateinit var countryList: MutableLiveData<ArrayList<InfoModelData>>
     var apiFailResponse= MutableLiveData<ApiFailModel>()
    init {
        repositoryViewModel = RepositoryViewModel(objApplication)
        countryList= MutableLiveData<ArrayList<InfoModelData>>()
        apiFailResponse.value= ApiFailModel()
    }
    //Calling API
    fun getCountryInformation() {
        repositoryViewModel.retrieveCountryFeaturesData(this)
    }
    //Success callback
    override fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?) {
        if (data != null) {
            countryList.value=data.value
        }
    }
    //Failure callback
    override fun onError(error: String?) {
        apiFailResponse.value=ApiFailModel(false)
    }

}