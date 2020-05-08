package com.techm.telstra.viewmodel

import androidx.lifecycle.MutableLiveData
import com.techm.telstra.model.InfoModelData

/**
 * This interface for handling retrofit callbacks
 */
interface ResponseCallback {
    fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?)
    fun onError(error: String?)
}