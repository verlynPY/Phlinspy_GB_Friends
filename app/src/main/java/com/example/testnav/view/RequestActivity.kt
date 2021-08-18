package com.example.testnav.view

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
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
import com.example.testnav.model.Request
import com.example.testnav.viewmodel.MainViewModel
import com.example.testnav.viewmodel.RoomViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatDialog


class RequestActivity : ComponentActivity() {

    private val viewModel: RoomViewModel by viewModels()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val Id = bundle!!.getCharSequence("Id")
        val UserName = bundle.getCharSequence("UserName")

        val personJsonString = bundle!!.getString("REQUEST")
        val request = gson.fromJson(personJsonString, Request::class.java)

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
                                        viewModel.RemoveRequest(this@RequestActivity, request)
                                        finish()
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
                                        val ID = Id.toString().toInt()
                                        val chatService = QBChatService.getInstance()
                                        val isLoggedIn = chatService.isLoggedIn

                                            viewModel.CreateDialog(ID, request, this@RequestActivity)
                                            finish()


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

    override fun onStart() {
        super.onStart()

        val bundle = intent.extras
        val Id = bundle!!.getCharSequence("Id")

        viewModel.UpdateStatusView(applicationContext, Id.toString())
    }

    fun Toast(context: Context, message: String){
        val bg = LayoutInflater.from(context)
            .inflate(R.layout.bg_toast_successfuly, findViewById(R.id.bg_toast))
        val toast = Toast.makeText(context, "Top", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0,0)
        var textMessage = bg.findViewById<TextView>(R.id.message)
        textMessage.text = message
        toast.view = bg
        toast.show()
    }
}

