package com.example.testnav

import android.app.Application
import android.content.SharedPreferences

class App: Application() {


    companion object {
        private lateinit var instance: App

        fun getInstance(): App = instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

    }
}