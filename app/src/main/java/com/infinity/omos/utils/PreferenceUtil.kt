package com.infinity.omos.utils

import android.content.Context
import android.content.SharedPreferences

/**
 *  SharedPreference Util
 */
class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return prefs.getString(key, null).toString()
    }

    fun setString(key: String, str: String?) {
        prefs.edit().putString(key, str).apply()
    }

    fun getLong(key: String): Long {
        return prefs.getLong(key, -1L)
    }

    fun setLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, -1)
    }

    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
}