package com.techm.telstra.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.techm.telstra.model.ApiFailModel
import com.techm.telstra.model.InfoModelData
import com.techm.telstra.viewmodel.repositoty.RepositoryViewModel
import org.jetbrains.annotations.NotNull

/**
 * This class for implementing viewModel
 * */
class CountryViewModel(@NotNull application: Application) : AndroidViewModel(application),
    ResponseCallback {
    private var objApplication: Application = application
    private lateinit var repositoryViewModel: RepositoryViewModel
    lateinit var countryList: MutableLiveData<ArrayList<InfoModelData>>
    var apiFailResponse = MutableLiveData<ApiFailModel>()

    /**
     * init for to initialize objects
     * */
    init {
        repositoryViewModel = RepositoryViewModel(objApplication)
        countryList = MutableLiveData<ArrayList<InfoModelData>>()
        apiFailResponse.value = ApiFailModel()
        repositoryViewModel.retrieveCountryFeaturesData(this)
    }

    /**
     * Calling server API
     */
    fun getCountryInformation() {
        repositoryViewModel.retrieveCountryFeaturesData(this)
    }

    /**
     * Success response callback from Repository
     * @param data for get updated list from API
     */
    override fun onSuccess(data: MutableLiveData<ArrayList<InfoModelData>>?) {
        if (data != null) {
            countryList.value = data.value
        }
    }

    /**
     * Failure response callback from Repository
     * @param error for get error message
     */
    override fun onError(error: String?) {
        apiFailResponse.value = ApiFailModel(false)
    }
}