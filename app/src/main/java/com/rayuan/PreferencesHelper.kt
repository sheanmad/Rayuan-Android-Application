package com.rayuan

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val preferencesName = "switchTheme"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setTheme(key: String, value: Boolean){
        editor.putBoolean(key, value)
            .apply()
    }
    fun getTheme(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

}