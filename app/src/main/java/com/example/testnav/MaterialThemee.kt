package com.example.testnav

import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

object MaterialThemee {



    val Yellow = Color(255,220,7)
    val yellow_Mostash = Color(230,187,0)
    val Gray_Ligth = Color(210,210,210)
    val Gray_Dark = Color(30,30,30)
    val White = Color(255,255,255)
    val Black = Color(0,0,0)

    val darkColor = darkColors(
            primary = Yellow,
            onPrimary = Gray_Dark,
            secondary = Black,
            onSecondary = Color(100,100,100),
            background = Color(0,100,100),
            onBackground = Black
    )

    val lightColor = lightColors(
            primary = Yellow,
            onPrimary = Gray_Ligth,
            secondary = Black,
            onSecondary = Color(0,0,0),
            background = Color(0,150,1,50),
            onBackground = White
    )

    val MyTypography = Typography(
        h3 = TextStyle(

        )
    )

}