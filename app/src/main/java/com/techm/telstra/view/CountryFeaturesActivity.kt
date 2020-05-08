package com.techm.telstra.view

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techm.telstra.R
import com.techm.telstra.adapter.CountryAdapter
import com.techm.telstra.utils.Constant
import com.techm.telstra.utils.LocalSharedPreferences
import com.techm.telstra.utils.NetworkConnection
import com.techm.telstra.viewmodel.CountryViewModel
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
        setupProgressDialog()

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

            hideProgressDialog()
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
                    hideProgressDialog()
                    swipeToRefresh.isRefreshing = false
                    Toast.makeText(this, Constant.serviceFailureError, Toast.LENGTH_SHORT).show()
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
                showProgressDialog()
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
                showProgressDialog()
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
    private fun setupProgressDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
    }

    private fun showProgressDialog() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    private fun hideProgressDialog() {
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
