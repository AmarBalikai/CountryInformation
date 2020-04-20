package com.example.countryinformation.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.countryinformation.application.ApplicationContext.Companion.context

class LocalSharedPreferences {
    lateinit var mSharedPreferences: SharedPreferences
    lateinit var sharedPreferencesEdit: SharedPreferences.Editor

    constructor() {
        mSharedPreferences =
            context.getSharedPreferences(Constant.countryInfo, Context.MODE_PRIVATE)
        sharedPreferencesEdit = mSharedPreferences.edit()
    }

    /*fun initSharedPreference(context: Application){
       // if (mSharedPreferences == null)
            mSharedPreferences =
                context.getSharedPreferences(Constentes.countryInfo, Context.MODE_PRIVATE)

    }*/


    fun getString(text: String): String? {
        if (mSharedPreferences != null)
            return mSharedPreferences.getString(Constant.countryName, "")

        return ""
    }

    fun putString(text: String, value: String?) {
        sharedPreferencesEdit.putString(text, value)
        sharedPreferencesEdit.apply()
        sharedPreferencesEdit.commit()

    }

}