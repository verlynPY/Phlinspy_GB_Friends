package com.example.testnav.model.Preferences

import android.app.Activity
import android.content.Context
import com.example.testnav.App
import com.example.testnav.model.SettingFilter
import com.google.gson.Gson
import com.quickblox.users.model.QBUser

object SharedPreferences {

    private val gson = Gson()

    val QBUSER = "QBUSER"

    val QBUSERDATA = "QBUSERDATAAUTHENTICATION"

    val storage = App.getInstance().getSharedPreferences(QBUSER, 0)

    fun SaveUser(user: QBUser){
            val jsonObject = gson.toJson(user)
            storage.edit().putString(QBUSERDATA, jsonObject).apply()
        }

    fun GetCurrentUser(): QBUser{
        var userjson = storage.getString(QBUSERDATA, "")
        var user = gson.fromJson(userjson, QBUser::class.java)
        return user
    }

    }



