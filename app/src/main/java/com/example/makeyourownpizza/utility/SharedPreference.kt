package com.example.makeyourownpizza.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {

    private val PREF_NAME = "PIZZATIMER"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun saveString(KEY_NAME: String, value: String) {

        val editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun saveLong(KEY_NAME: String, value: Long){
        val editor = sharedPref.edit()
        editor.putLong(KEY_NAME,value)
        editor.apply()
    }

    fun saveBoolean(KEY_NAME: String, value: Boolean){
        val editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME,value)
        editor.apply()
    }

    fun saveInt(KEY_NAME: String, value: Int){
        val editor = sharedPref.edit()
        editor.putInt(KEY_NAME,value)
        editor.apply()
    }


    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, " ")
    }

    fun getValueLong(KEY_NAME: String): Long {

        return sharedPref.getLong(KEY_NAME, 1000 * 60 * 90)
    }

    fun getValueBool(KEY_NAME: String):Boolean{
        return sharedPref.getBoolean(KEY_NAME,false)
    }


    fun getInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun clearSharedPreference(KEY_NAME:String){

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

}