package com.example.testnav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testnav.ui.theme.TestNavTheme
import com.example.testnav.view.MainActivity
import com.example.testnav.view.MapsActivity
import com.example.testnav.view.buttomModifier


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colors = if (isSystemInDarkTheme())
                MaterialThemee.darkColor else MaterialThemee.lightColor) {
                Surface(color = Color(0,0,0)) {
                    Login(applicationContext, onClick = { finish() })
                }

            }
        }
    }
}

    //@Preview
    @Composable
    fun Login(context: Context, onClick :() -> Unit){


        Column(){

            Row() {
                IconButton(onClick = onClick) {
                    Icon(vectorResource(R.drawable.ic_back), tint = MaterialThemee.yellow_Mostash)
                }
                Column(modifier = Modifier.absolutePadding(top = 40.dp)) {
                    Image(
                        imageResource(id = R.drawable.googlemaps_gold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(120.dp)
                            .absolutePadding(right = 30.dp, bottom = 10.dp)

                    )
                }
            }

            Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Card(modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topLeft = 100.dp)),
                        backgroundColor = Color(255, 255, 255)

                )
                {
                    Column(modifier = Modifier.padding(20.dp)) {

                        var userName = remember { mutableStateOf("") }
                        var passWord = remember { mutableStateOf("") }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
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
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(value = passWord.value,
                                onValueChange = { passWord.value = it },
                                inactiveColor = MaterialTheme.colors.onSecondary,
                                activeColor = MaterialTheme.colors.onSecondary,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = "PassWord") }

                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        Button(onClick = {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)

                        },colors =
                        ButtonConstants.defaultButtonColors(
                            backgroundColor = MaterialTheme.colors.secondary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(60.dp)
                                .clip(RoundedCornerShape(30.dp))) {
                            Text(text = "Sing In",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialThemee.yellow_Mostash
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(modifier = Modifier.fillMaxWidth().preferredHeight(180.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)
                                }, colors =
                                ButtonConstants.defaultButtonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                                modifier = buttomModifier
                            ) {
                                Image(
                                    imageResource(id = R.drawable.google),
                                    modifier = Modifier.absolutePadding(right = 8.dp)
                                )

                                Text(
                                    text = "Sign In",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialThemee.yellow_Mostash
                                )
                            }
                            Spacer(modifier = Modifier.preferredWidth(10.dp))

                            Button(
                                onClick = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)

                                }, colors =
                                ButtonConstants.defaultButtonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                                modifier = buttomModifier
                            ) {
                                Image(
                                    imageResource(id = R.drawable.facebook),
                                    modifier = Modifier.absolutePadding(right = 8.dp)
                                )

                                Text(
                                    text = "Sign In",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialThemee.yellow_Mostash
                                )
                            }
                        }
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Don't have any account?, Sing Up", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

