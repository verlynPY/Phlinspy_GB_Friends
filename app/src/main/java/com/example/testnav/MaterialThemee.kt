package com.example.testnav

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object MaterialThemee {

    val darkColor = darkColors(
            primary = Color(100,100,100),
            onSecondary = Color(100,100,100),
            background = Color(0,100,100)
    )

    val lightColor = lightColors(
            primary = Color(0,0,0),
            onSecondary = Color(0,0,0,),
            background = Color(0,150,1,50)
    )

}