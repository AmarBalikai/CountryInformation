package com.example.countryinformation.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.countryinformation.R
import com.example.countryinformation.utils.Constentes

class ApplicationContext : Application() {
    lateinit var sharedPreferencesEdit: SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences


    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferencesEdit =
            getSharedPreferences(Constentes.countryInfo, Context.MODE_PRIVATE).edit()

        sharedPreferences =
            getSharedPreferences(Constentes.countryInfo, Context.MODE_PRIVATE)

        context = this
    }

    fun setPreferenceString(text: String, value: String) {
        //sharedPreferencesEdit.putString(text, value)
        sharedPreferencesEdit.putString(Constentes.countryName, "")
        sharedPreferencesEdit.apply()
        sharedPreferencesEdit.commit()

    }

    fun getPreferenceString(text: String): String? {
       return sharedPreferences.getString(text, "")
    }

    fun getContext(): Context {
        return context
    }

}