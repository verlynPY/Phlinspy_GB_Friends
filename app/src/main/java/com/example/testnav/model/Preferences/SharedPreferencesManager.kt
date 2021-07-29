package com.example.testnav.model.Preferences

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.testnav.model.SettingFilter
import com.google.gson.Gson

class SharedPreferencesManager {


    private val gson = Gson()

    fun SaveSetting(activity: Activity?, settingFilter: SettingFilter){
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            val gson = Gson()
            val jsonObject = gson.toJson(settingFilter)
            putString(settingFilter.UserId, jsonObject)
            commit()
        }

    }

    fun ReadSetting(activity: Activity?, UserId: String): SettingFilter{

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        var settingFilterr = SettingFilter("oxGvYueyE4hflxgkEJEH9YBuLFf1", 30, 30,
            10F, "Mujer")

        val jsonObject = gson.toJson(settingFilterr)
        val json = sharedPref!!.getString(UserId, jsonObject)
        val settingFilter = gson.fromJson(json, SettingFilter::class.java)
        //Log.e(TAG , "DATA    ${settingFilter}")
        return settingFilter

    }

}