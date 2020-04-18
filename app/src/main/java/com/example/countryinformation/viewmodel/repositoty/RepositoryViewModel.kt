package com.example.countryinformation.viewmodel.repositoty

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emedinaa.kotlinmvvm.data.ApiClient
import com.example.countryinformation.model.CountryModel
import com.example.countryinformation.model.InfoModelData
import com.example.countryinformation.roomdatabase.CountryDao
import com.example.countryinformation.roomdatabase.CountryDatabase
import com.example.countryinformation.roomdatabase.CountryEntity
import com.example.countryinformation.viewmodel.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel {

    private var countryDao: CountryDao
    private lateinit var listCountry: LiveData<List<CountryEntity>>
    lateinit var listData:ArrayList<CountryEntity>
    lateinit var sharedPreferences: SharedPreferences.Editor
    private lateinit var countryDatabase: CountryDatabase

    public constructor(application: Application) {
        sharedPreferences =
            application.getSharedPreferences("CountryInfo", Context.MODE_PRIVATE).edit()
        countryDatabase = CountryDatabase.invoke(application)
        countryDao = countryDatabase.getCountryDao()
    }


    fun getDataFromServer(objCallback: ResponseCallback) {
        var listResponse: MutableLiveData<ArrayList<InfoModelData>>
        listResponse=MutableLiveData()
        //listResponse.value=ArrayList()
        val data: Call<CountryModel>? = ApiClient.build()?.getList()
        val enqueue = data?.enqueue(object : Callback<CountryModel> {
            override fun onResponse(call: Call<CountryModel>, response: Response<CountryModel>) {
                if (response.isSuccessful) {
                    sharedPreferences.putString("countryName", response.body()?.title)
                    sharedPreferences.apply()
                    sharedPreferences.commit()

                    listResponse.value = response.body()?.rows
                    objCallback.onSuccess(listResponse)


                }
            }
            override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                objCallback.onError(t.message)
               // listResponse=null
            }
        })
    }

    fun saveDataToDatabase(data: ArrayList<CountryEntity>?) {
        countryDao.insertAll(data)
    }

    fun deleteAll() {

    }

    fun getDataFromDatabase(): LiveData<List<CountryEntity>> {
        countryDao = countryDatabase.getCountryDao()
        listCountry = countryDao.getAllCountries()
        return listCountry
    }

    class MyAsyncTask internal constructor(context: Application, countryDao: CountryDao) :
        AsyncTask<ArrayList<CountryEntity>, String, String?>() {
        var countryDao: CountryDao = countryDao

        var context = context
        // private var resp: String? = null
        // private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            val activity = context
            /*if (activity == null || activity.isFinishing) return
            activity.progressBar.visibility = View.VISIBLE*/
        }

        override fun doInBackground(vararg params: ArrayList<CountryEntity>?): String? {
            //publishProgress("Sleeping Started") // Calls onProgressUpdate()
            try {
                //countryDao.insertAll(params);
            } catch (e: InterruptedException) {
                e.printStackTrace()

            } catch (e: Exception) {
                e.printStackTrace()

            }

            return ""
        }

        override fun onPostExecute(result: String?) {

            /*val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
            activity.progressBar.visibility = View.GONE
            activity.textView.text = result.let { it }
            activity.myVariable = 100*/
        }

    }
}