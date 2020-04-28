package com.example.countryinformation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.countryinformation.model.InfoModelData
/**
 * This interface for handling retrofit callbacks
 */
interface ResponseCallback
{
    fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?)
    fun onError(error:String?)
}