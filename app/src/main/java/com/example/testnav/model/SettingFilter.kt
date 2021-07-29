package com.example.testnav.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class SettingFilter(

    var UserId: String? = null,
    val AgeMax: Int = 25,
    val AgeMin: Int = 16,
    val Distance: Float = 1F,
    val Gender: String? = null

)
