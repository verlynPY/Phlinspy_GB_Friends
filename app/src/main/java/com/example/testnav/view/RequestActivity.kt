package com.example.testnav.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testnav.MaterialThemee
import com.example.testnav.R


class RequestActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val Id = bundle!!.getCharSequence("Id")
        val UserName = bundle.getCharSequence("UserName")

        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme())
                    MaterialThemee.darkColor else MaterialThemee.lightColor
            ) {
                ConstraintLayout(modifier = Modifier.background(MaterialTheme.colors.onBackground)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                        IconButton(onClick = {
                            finish()
                        }){
                            Icon(vectorResource(R.drawable.ic_back), tint = MaterialTheme.colors.primary)
                        }
                    }
                    Column(modifier = Modifier.fillMaxSize()) {

                            Column {
                                Row(modifier = Modifier.fillMaxWidth().absolutePadding(top = 40.dp), horizontalArrangement = Arrangement.Center) {
                                    Image(
                                        imageResource(R.drawable.profile),
                                        modifier = Modifier
                                            .preferredWidth(240.dp)
                                            .preferredHeight(240.dp)
                                            .absolutePadding(top = 5.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "$UserName",
                                        modifier = Modifier.absolutePadding(
                                            top = 30.dp,
                                            left = 10.dp
                                        ),
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "Has sent you a request",
                                        modifier = Modifier.absolutePadding(
                                            top = 2.dp,
                                            left = 10.dp
                                        ),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Light,
                                        textAlign = TextAlign.Center
                                    )
                                }


                        }

                        Column(
                            modifier = Modifier.fillMaxHeight()
                                .absolutePadding(left = 10.dp, right = 10.dp, bottom = 20.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                                Button(
                                    onClick = {

                                    }, modifier = Modifier.preferredWidth(160.dp)
                                        .preferredHeight(55.dp)
                                        .absolutePadding(10.dp)
                                        .clip(RoundedCornerShape(20.dp)),
                                    colors = ButtonConstants.defaultButtonColors(backgroundColor = MaterialTheme.colors.onPrimary)

                                ) {
                                    Text(
                                        text = "Delete",
                                        fontSize = 20.sp,
                                        color = Color(240, 0, 0),
                                        fontWeight = FontWeight.Light
                                    )
                                }
                                Button(
                                    onClick = {

                                    }, modifier = Modifier.preferredWidth(160.dp)
                                        .preferredHeight(55.dp)
                                        .absolutePadding(10.dp)
                                        .clip(RoundedCornerShape(20.dp)),
                                    colors = ButtonConstants.defaultButtonColors(backgroundColor = MaterialTheme.colors.onPrimary)

                                ) {
                                    Text(
                                        text = "Accept",
                                        fontSize = 20.sp,
                                        color = Color(0, 240, 0),
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                        }

                    }


                }
            }
        }
    }
}

