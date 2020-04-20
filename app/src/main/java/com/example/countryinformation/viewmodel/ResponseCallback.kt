package com.example.countryinformation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.model.InfoModelData

interface ResponseCallback
{
    fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?)
    fun onError(error:String?)


}