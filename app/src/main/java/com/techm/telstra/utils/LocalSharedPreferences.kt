package com.techm.telstra.utils

import android.content.Context
import android.content.SharedPreferences
import com.techm.telstra.application.ApplicationContext.Companion.context

/**
 * This class for storing objects in SharedPreferences
 */
class LocalSharedPreferences {
    private var mSharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constant.countryInfo, Context.MODE_PRIVATE)
    private lateinit var sharedPreferencesEdit: SharedPreferences.Editor

    init {
        sharedPreferencesEdit = mSharedPreferences.edit()
    }

    /**
     * @return Country name
     */
    fun getString(text: String): String? {
        if (mSharedPreferences != null)
            return mSharedPreferences.getString(Constant.countryName, "")

        return ""
    }
    /**
     * This method for storing Country name
     */
    fun putString(text: String, value: String?) {
        sharedPreferencesEdit.putString(text, value)
        sharedPreferencesEdit.apply()
        sharedPreferencesEdit.commit()
    }
}