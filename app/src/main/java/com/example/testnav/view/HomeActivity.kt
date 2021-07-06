package com.example.testnav.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testnav.LoginActivity
import com.example.testnav.MaterialThemee
import com.example.testnav.R
import com.example.testnav.RegisterActivity
import com.example.testnav.model.Request
import com.example.testnav.viewmodel.RoomViewModel


class HomeActivity : ComponentActivity() {

    private val viewmodel: RoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val request = Request(1, "nose", "nose")

        //viewmodel.AddRequest(request, applicationContext)
        viewmodel.getRequests(applicationContext)


        setContent {

            MaterialTheme(
                colors = if (isSystemInDarkTheme())
                    MaterialThemee.darkColor else MaterialThemee.lightColor
            ) {
                ConstraintLayout(modifier = Modifier.background(MaterialTheme.colors.onBackground).fillMaxSize()) {
                    HomeView(applicationContext)
                }
            }
        }
    }
}


    @Composable
    fun HomeView(context: Context){
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Image(imageResource(R.drawable.googlemaps_gold),
                modifier = Modifier
                    .preferredWidth(140.dp)
                    .preferredHeight(140.dp)
                    .absolutePadding(top = 40.dp)
            )
        }

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom){

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(350.dp),
                shape = RoundedCornerShape(topLeft = 45.dp, topRight = 45.dp),
            )
            {
                ConstraintLayout(modifier = Modifier
                    .background(MaterialThemee.yellow_Mostash)
                    .fillMaxSize())
                {
                    Column() {
                        Text(
                            text = "Welcome",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0, 0, 0),
                            modifier = Modifier.absolutePadding(left = 20.dp, top = 55.dp)
                        )
                        Text(
                            text = "Already you can see who live near of you, Sing Up and explore.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0, 0, 0),
                            modifier = Modifier.absolutePadding(left = 20.dp, right = 20.dp, top = 5.dp)
                        )
                    }

                    Row(modifier = Modifier.absolutePadding(bottom = 30.dp).fillMaxWidth().fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(onClick = {
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        },
                            modifier = buttomModifier,
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(0,0,0))
                        )
                        {
                            Text(text = "Sign In", color = MaterialTheme.colors.onBackground)
                        }
                        Spacer(modifier = Modifier.preferredWidth(10.dp))
                        Button(onClick = {
                            val intent = Intent(context, RegisterActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        },
                            modifier = buttomModifier,
                            shape = RoundedCornerShape(30.dp
                            ),
                            colors = ButtonConstants.defaultButtonColors(backgroundColor = Color(240,240,240))
                        )
                        {
                            Text(text = "Sign Up")
                        }
                    }

                }
            }
        }
    }

