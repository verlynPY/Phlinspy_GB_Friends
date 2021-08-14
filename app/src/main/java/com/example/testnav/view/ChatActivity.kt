package com.example.testnav.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.testnav.MaterialThemee
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.viewmodel.RoomViewModel
import com.google.gson.Gson
import com.quickblox.chat.QBChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.flow.collect
import org.jivesoftware.smackx.muc.DiscussionHistory

class ChatActivity : ComponentActivity() {

    private val viewModel: RoomViewModel by viewModels()
    private var currentUser = QBUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier =
            Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                var List = ArrayList<QBChatMessage>()
                val Active = remember { mutableStateOf(false) }


                currentUser = SharedPreferences.GetCurrentUser()
                Log.e(TAG, "$currentUser")

                QBUsers.getUser(currentUser.id).performAsync(object : QBEntityCallback<QBUser> {
                    override fun onSuccess(qbUser: QBUser?, bundle: Bundle?) {
                        Log.e(TAG, "$qbUser")

                        qbUser!!.password = currentUser.password
                        qbUser.id = currentUser.id
                        val bundle = intent.extras
                        val gson = Gson()
                        val personJsonString = bundle!!.getString("DIALOG")
                        //val dialog = Utils.getGsonParser()!!.fromJson(personJsonString, QBChatDialog::class.java)
                        val dialog = gson.fromJson(personJsonString, QBChatDialog::class.java)

                        //dialog.initForChat(QBChatService.getInstance())


                    lifecycleScope.launchWhenCreated {
                        viewModel.EmitChat(dialog)
                    viewModel.chatUiState.collect { chatUiState ->
                        when(chatUiState){
                            is RoomViewModel.ChatUiState.Success -> {
                                if(chatUiState.dialog != null){
                                    List = chatUiState.dialog!!
                                    Active.value = true
                                }

                            }
                            is RoomViewModel.ChatUiState.Error -> {
                                Log.e(TAG, "ChatUiState Error")
                            }
                        }
                    }
                }

                        //Login(qbUser!!)
                    }

                    override fun onError(e: QBResponseException?) {
                        Log.e(TAG, "$e")
                    }
                })

                //val chatService = QBChatService.getInstance()
                //val isLoggedIn = chatService.isLoggedIn

                    //JoinDialog(dialog)

                //val dialog = DialogUtils.buildPrivateDialog(userId)
                //var qbChatDialog = QBChatDialog()


                

                TextField()

            }
            /*LazyColumn(){
                itemsIndexed(items = chatt){ index, chat ->
                    Column(modifier = Modifier.padding(top = 1.dp, start = 6.dp, bottom = 1.dp, end = 6.dp)) {
                        Text(text = "${chat.body}")
                        if(chat.senderId.equals(SenderID)){
                            ItemRigth(chat)
                        }
                        else{
                            ItemLeft(chat)
                        }

                    }
                }
            }*/
        }
    }

    fun Login(user: QBUser){
        QBChatService.getInstance().login(user, object : QBEntityCallback<Void> {
            override fun onSuccess(v: Void?, b: Bundle?) {
                Log.e(ContentValues.TAG, "Login to Chat Good")
                val bundle = intent.extras
                val gson = Gson()
                val personJsonString = bundle!!.getString("DIALOG")
                //val dialog = Utils.getGsonParser()!!.fromJson(personJsonString, QBChatDialog::class.java)
                val dialog = gson.fromJson(personJsonString, QBChatDialog::class.java)
                dialog.initForChat(QBChatService.getInstance())
                //val chatService = QBChatService.getInstance()
                //val isLoggedIn = chatService.isLoggedIn

                JoinDialog(dialog)
            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Login to Chat: ${e!!.message}")
            }
        })
    }

    fun JoinDialog(dialog: QBChatDialog){
        Log.e(ContentValues.TAG, "Joinnnn:    $dialog")
        val history = DiscussionHistory()
        history.maxStanzas = 0
        //SendMessage(dialog, "Message Sent")
        //ReadMessage(dialog)
        viewModel.SendMessage(dialog, "Message Sentt")

        dialog.join(history, object : QBEntityCallback<Void> {
            override fun onSuccess(o: Void?, bundle: Bundle?) {
                Log.e(ContentValues.TAG, "Join Good")
                //viewModel.SendMessage(dialog, "Texto")

            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Join Error: ${e!!.message}")
            }
        })



    }

}




@Composable
fun ItemRigth(chat: QBChatMessage){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
        Box(modifier = Modifier
            .wrapContentSize()
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(MaterialThemee.yellow_Mostash)
        ){
            Row() {
                Text(text = "Messag Sent", fontSize = 16.sp, modifier = Modifier.padding(10.dp))
            }
        }


    }
}



@Composable
fun ItemLeft(chat: QBChatMessage){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Box(modifier = Modifier
            .wrapContentSize()
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color(100,100,100,50))
        ) {

            Row() {
                Text(text = "Message Received", fontSize = 16.sp, modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Composable
fun TextField(){
    Column {
        var messageText = remember { mutableStateOf("")}
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = messageText.value,
                onValueChange = { messageText.value = it },
                modifier = Modifier
                    .absolutePadding(left = 35.dp).border(
                        width = 1.dp,
                        brush = SolidColor(MaterialThemee.yellow_Mostash),
                        shape = RoundedCornerShape(25.dp)
                    ).background(
                        brush = SolidColor(Color.Transparent),
                        shape = RoundedCornerShape(25.dp)
                    ).clip(RoundedCornerShape(25.dp)),
                placeholder = { Text("Type a Message") },
                activeColor = Color.Transparent
            )
            //IconButton(onClick = onClick){
            Box(modifier = Modifier
                .absolutePadding(left = 10.dp, bottom = 15.dp)
                .height(55.dp)
                .width(55.dp)
                .clip(CircleShape)
                .background(Color(100,100,100, 50)),
                contentAlignment = Alignment.Center

            ) {
                Icon(Icons.Default.Send, Modifier.size(30.dp), tint = MaterialThemee.yellow_Mostash)
            }
            //}
        }
    }
}



