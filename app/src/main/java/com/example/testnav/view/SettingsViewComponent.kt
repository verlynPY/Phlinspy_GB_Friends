package com.example.testnav.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.testnav.R


    @Preview
    @Composable
    fun Profile() {


    Column(modifier = Modifier.fillMaxSize()) {
        val imageDefault = R.drawable.profile
        Row(
            modifier = Modifier.fillMaxWidth().padding(30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageResource(imageDefault),
                modifier = Modifier
                    .preferredWidth(240.dp)
                    .preferredHeight(240.dp)
                    .clip(CircleShape),
                Alignment.Center,
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Row() {
                Text(
                    text = "Verlyn Luna Mateo",
                    modifier = Modifier.absolutePadding(left = 24.dp),
                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {

                }) {
                    Icon(
                        vectorResource(R.drawable.ic_edit),
                        modifier = Modifier.absolutePadding(bottom = 10.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Row() {
                Text(
                    text = "Hombre",
                    modifier = Modifier.absolutePadding(left = 24.dp),
                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {

                }) {
                    Icon(
                        vectorResource(R.drawable.ic_edit),
                        modifier = Modifier.absolutePadding(bottom = 10.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }


        }
    }
    }



