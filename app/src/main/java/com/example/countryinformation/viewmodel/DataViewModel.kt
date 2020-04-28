package com.example.countryinformation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.utils.Constant
import com.example.countryinformation.viewmodel.repositoty.RepositoryViewModel
import org.jetbrains.annotations.NotNull

class DataViewModel(@NotNull application: Application) : AndroidViewModel(application),ResponseCallback{
   private var objApplication: Application = application
    private lateinit var repositoryViewModel: RepositoryViewModel

    lateinit var dataList: MutableLiveData<ArrayList<InfoModelData>>

    init {
        repositoryViewModel = RepositoryViewModel(objApplication)
        dataList= MutableLiveData<ArrayList<InfoModelData>>()

    }
    //Calling API
    fun getCountryInformation() {
        repositoryViewModel.retrieveCountryFeaturesData(this)
    }
    //Success callback
    override fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?) {
        if (data != null) {
            dataList.value=data.value
        }
    }
    //Failure callback
    override fun onError(error: String?) {
        Toast.makeText(getApplication(),Constant.somethingWentWrong,Toast.LENGTH_SHORT).show()
        dataList.value= ArrayList()
    }

}