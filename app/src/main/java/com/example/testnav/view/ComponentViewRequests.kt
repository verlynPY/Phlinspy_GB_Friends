package com.example.testnav.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testnav.R
import com.example.testnav.model.User
import com.example.testnav.model.Utils.OpenRequetAtivity

@Composable
    fun ShowRequests(context: Context, list: ArrayList<User>){
        LazyColumn{
            itemsIndexed(items = list){ index, list ->
                Column(modifier = Modifier.padding(1.dp)
                    .clickable {
                        OpenRequetAtivity(context, list)
                    }
                ) {
                    Card(modifier = Modifier.fillMaxWidth().preferredHeight(80.dp),
                        backgroundColor = MaterialTheme.colors.onBackground
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                            Image(
                                imageResource(R.drawable.profile), modifier = Modifier.clip(
                                    CircleShape
                                )
                            )
                            Text(
                                text = "${list.UserName}",
                                modifier = Modifier.absolutePadding(top = 10.dp, left = 10.dp),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Box(modifier = Modifier.fillParentMaxWidth(), Alignment.CenterEnd) {
                                Icon(vectorResource(R.drawable.ic_point),modifier = Modifier.absolutePadding(top = 25.dp), tint = MaterialTheme.colors.primary)
                            }

                        }
                    }
                }

            }
        }
    }

    @Composable
    fun CircularIndicator(){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
    }


