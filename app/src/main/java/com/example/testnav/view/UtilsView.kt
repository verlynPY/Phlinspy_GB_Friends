package com.example.testnav.view

import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

val buttomModifier = Modifier
    .preferredWidth(160.dp)
    .preferredHeight(60.dp)
    .clip(RoundedCornerShape(30.dp))

@Composable
fun Dialog(){
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {

        androidx.compose.ui.window.Dialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },

            ) {
            val property = remember { mutableStateOf("") }
            Card(
                modifier = Modifier
                    .preferredHeight(120.dp)
                    .preferredWidth(300.dp),
                shape = RoundedCornerShape(20.dp)
            )
            {

            }
        }


    }
}