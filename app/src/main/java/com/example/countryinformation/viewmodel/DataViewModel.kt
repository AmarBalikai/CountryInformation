package com.example.countryinformation.viewmodel.repositoty

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.countryinformation.adapter.CountryAdapter
import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.roomdatabase.CountryEntity
import com.example.countryinformation.viewmodel.ResponseCallback
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class DataViewModel(@NotNull application: Application) : AndroidViewModel(application),ResponseCallback{
    var objApplication: Application = application
    private lateinit var repositoryViewModel: RepositoryViewModel
     var arrList=ArrayList<CountryEntity>()
    lateinit var dataList: MutableLiveData<ArrayList<InfoModelData>>

    init {
        repositoryViewModel = RepositoryViewModel(objApplication)
        dataList= MutableLiveData<ArrayList<InfoModelData>>()

    }
    fun getData() {
        repositoryViewModel.getDataFromServer(this)
    }

    override fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?) {
        if (data != null) {
            dataList.value=data.value
        }
    }

    override fun onError(error: String?) {
        Toast.makeText(getApplication(),error,Toast.LENGTH_SHORT).show()
        dataList.value= ArrayList()
    }

}