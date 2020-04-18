package com.example.countryinformation.view

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.example.countryinformation.utils.Constentes
import com.example.countryinformation.viewmodel.repositoty.DataViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mDataViewModel: DataViewModel
    private lateinit var mAdapter: CountryAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    private lateinit var linearLayoutManager: LinearLayoutManager
// android:text="@{data.title==null?`No title`:data.title}"
    //lateinit var binding:ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        setupDialog()
        sharedPreferences =
            application.getSharedPreferences(Constentes.countryInfo, Context.MODE_PRIVATE)

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
            if(sharedPreferences.getString(Constentes.countryName, "")!="")
            {
                this.supportActionBar?.title =sharedPreferences.getString(Constentes.countryName, "")
            }

            //setup list
            mAdapter.setList(countryList)

        })

    }

    private fun callApi() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isNetworkConnected()) {
                showDialog()
                mDataViewModel.getData()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.device_not_connected_to_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            if (isNetworkConnectedKitkat()) {
                showDialog()
                mDataViewModel.getData()

            } else {
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

    private fun isNetworkConnectedKitkat(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isActiveNetworkMetered
        //return isMetered
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

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
