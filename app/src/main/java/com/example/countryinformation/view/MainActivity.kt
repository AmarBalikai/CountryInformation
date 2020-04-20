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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.countryinformation.R
import com.example.countryinformation.adapter.CountryAdapter
import com.example.countryinformation.utils.Constant
import com.example.countryinformation.utils.LocalSharedPreferences
import com.example.countryinformation.utils.NetworkConnection
import com.example.countryinformation.viewmodel.repositoty.DataViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mDataViewModel: DataViewModel
    private lateinit var mAdapter: CountryAdapter
   // lateinit var sharedPreferences: SharedPreferences
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var mlocalSharedPreferences: LocalSharedPreferences
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mlocalSharedPreferences = LocalSharedPreferences()
        mDataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        setupDialog()
     //   sharedPreferences = mlocalSharedPreferences.getSharedPre(application)
        //application.getSharedPreferences(Constentes.countryInfo, Context.MODE_PRIVATE)
       // mlocalSharedPreferences.initSharedPreference(application)

        // Calling API
        callApi()

        swipeToRefresh.setOnRefreshListener(OnRefreshListener {
            callApi()

        })
        mAdapter = CountryAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        country_list.layoutManager = linearLayoutManager
        country_list.adapter = mAdapter
        mDataViewModel.dataList.observe(this, Observer { countryList ->

            hideDialog()
            swipeToRefresh.isRefreshing = false
            //update title
            /*if (sharedPreferences.getString(Constentes.countryName, "") != "") {
                this.supportActionBar?.title =
                    sharedPreferences.getString(Constentes.countryName, "")
            }*/
            if (mlocalSharedPreferences.getString(Constant.countryName) != "") {
                this.supportActionBar?.title =
                    mlocalSharedPreferences.getString(Constant.countryName)
            }
            //setup list
            mAdapter.setList(countryList)

        })

    }

    private fun callApi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (NetworkConnection.isNetworkConnected()) {
                showDialog()
                mDataViewModel.getData()
            } else {
                if(swipeToRefresh.isRefreshing)
                {
                    swipeToRefresh.isRefreshing=false
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
                mDataViewModel.getData()

            } else {
                if(swipeToRefresh.isRefreshing)
                {
                    swipeToRefresh.isRefreshing=false
                }
                Toast.makeText(
                    applicationContext,
                    getString(R.string.device_not_connected_to_internet),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

    }

    /*private fun isNetworkConnectedKitkat(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isActiveNetworkMetered
        //return isMetered
    }*/

    /*@RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
*/
    fun showDialog() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    fun hideDialog() {
        if (dialog != null && dialog.isShowing) {
            dialog.hide()
            dialog.dismiss()
        }
    }

}
