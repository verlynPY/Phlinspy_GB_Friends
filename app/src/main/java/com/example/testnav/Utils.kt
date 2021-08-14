package com.example.testnav

import com.google.gson.GsonBuilder

import com.google.gson.Gson




object Utils {

    val PathFirebase = "Users"
    val PathRequests = "Requests"

    private var gson: Gson? = null

    fun getGsonParser(): Gson? {
        if (null == gson) {
            val builder = GsonBuilder()
            gson = builder.create()
        }
        return gson
    }

}