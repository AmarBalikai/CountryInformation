package com.example.countryinformation.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.countryinformation.application.ApplicationContext.Companion.context

class LocalSharedPreferences {
    private var mSharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constant.countryInfo, Context.MODE_PRIVATE)
    private lateinit var sharedPreferencesEdit: SharedPreferences.Editor

    init {
        sharedPreferencesEdit = mSharedPreferences.edit()
    }


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