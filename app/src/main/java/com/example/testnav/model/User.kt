package com.example.testnav.model

data class User (
    var Id: String = "",
    val UserName: String = "",
    val NumberPhone: Long = 0,
    var Age: Long = 0,
    val Hobby: String = "",
    val Gender: String = "",
    val Latitude: String = "",
    val Longitude: String = "",
    val Email: String = "",
    val PassWord: String = "",
    val statusview: Boolean = false
)