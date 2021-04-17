package com.example.testnav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testnav.ui.theme.TestNavTheme


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = if (isSystemInDarkTheme())
                MaterialThemee.darkColor else MaterialThemee.lightColor) {
                Surface(color = Color(0,0,0)) {
                    Login()
                }

            }
        }
    }
}

    @Preview
    @Composable
    fun Login(){

        Column(){


            Image(imageResource(id = R.mipmap.imgmaps), modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(200.dp)

            )
            Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)) {
                Card(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topLeft = 100.dp)),
                        backgroundColor = Color(255, 255, 255)

                )
                {
                    Column(modifier = Modifier.padding(20.dp)) {

                        var userName = remember { mutableStateOf("") }
                        var passWord = remember { mutableStateOf("") }
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(text = "Login", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold,
                                    textAlign = TextAlign.Justify
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                        OutlinedTextField(value = userName.value,
                                onValueChange = { userName.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = "UserName") }

                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        OutlinedTextField(value = passWord.value,
                                onValueChange = { passWord.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = "PassWord") }

                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(onClick = { }, modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(55.dp)
                                .clip(RoundedCornerShape(topLeft = 25.dp, bottomLeft = 25.dp,
                                        bottomRight = 30.dp))) {
                            Text(text = "SingIn", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Don't have any account?, Sing Up", fontWeight = FontWeight.Bold)
                        }


                    }
                }
            }

        }
    }

