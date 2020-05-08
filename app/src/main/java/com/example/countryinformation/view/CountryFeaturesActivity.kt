package com.example.countryinformation.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryinformation.R
import com.example.countryinformation.adapter.CountryAdapter
import com.example.countryinformation.utils.Constant
import com.example.countryinformation.utils.LocalSharedPreferences
import com.example.countryinformation.utils.NetworkConnection
import com.example.countryinformation.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This class for showing country list
 * */
class CountryFeaturesActivity : AppCompatActivity() {
    private lateinit var mDataViewModel: CountryViewModel
    private lateinit var mAdapter: CountryAdapter
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var mLocalSharedPreferences: LocalSharedPreferences
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLocalSharedPreferences = LocalSharedPreferences()
        mDataViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        setupDialog()

        /**
         * This method is calling API in ViewModel class
         */
       // getCountryFeaturesData()

        /**
         * Implemented swap to refresh listener
         */
        swipeToRefresh.setOnRefreshListener {
            getCountryFeaturesData()

        }
        /**
         * Setting blank adapter for initialize
         */
        mAdapter = CountryAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        country_list.layoutManager = linearLayoutManager
        country_list.adapter = mAdapter
        /**
         * Created observer for server list
         * @param countryList for to get updated list
         */
        mDataViewModel.countryList.observe(this, Observer { countryList ->

            hideDialog()
            swipeToRefresh.isRefreshing = false
            /**
             * Updating toolbar title
             */
            if (mLocalSharedPreferences.getString(Constant.countryName) != "") {
                this.supportActionBar?.title =
                    mLocalSharedPreferences.getString(Constant.countryName)
            }
            /**
             * Setting updated list to recyclerview adapter
             */
            mAdapter.setList(countryList)

        })
        /**
         * Created observer for error response
         */
        mDataViewModel.apiFailResponse.observe(this, Observer { apiFailResponse ->
            apiFailResponse.let {
                if (!apiFailResponse.responseSuccess) {
                    hideDialog()
                    swipeToRefresh.isRefreshing = false
                    Toast.makeText(this, Constant.somethingWentWrong, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
    /**
     * This method for get data from the viewModel
     */
    private fun getCountryFeaturesData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NetworkConnection.isNetworkConnected()) {
                showDialog()
                mDataViewModel.getCountryInformation()
            } else {
                if (swipeToRefresh.isRefreshing) {
                    swipeToRefresh.isRefreshing = false
                }
                Toast.makeText(
                    applicationContext,
                    getString(R.string.device_not_connected_to_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            if (NetworkConnection.isNetworkConnectedKitkat()) {
                showDialog()
                mDataViewModel.getCountryInformation()

            } else {
                if (swipeToRefresh.isRefreshing) {
                    swipeToRefresh.isRefreshing = false
                }
                Toast.makeText(
                    applicationContext,
                    getString(R.string.device_not_connected_to_internet),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }
    /**
     * Showing dialog when api call
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

    }
    /**
     * Showing dialog when api call
     */
    private fun showDialog() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }
    /**
     * Hiding dialog
     */
    private fun hideDialog() {
        if (dialog != null && dialog.isShowing) {
            dialog.hide()
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog.isShowing) {
            dialog.hide()
            dialog.dismiss()
        }
    }
}
