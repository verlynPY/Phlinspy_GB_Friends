package com.example.testnav.model.QuickBlox

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testnav.model.DAO.DataBaseBuilder
import com.example.testnav.model.DAO.DatabaseHelperImpl
import com.example.testnav.model.Preferences.SharedPreferences
import com.example.testnav.model.Request
import com.example.testnav.model.Utils
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBRequestGetBuilder
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jivesoftware.smackx.muc.DiscussionHistory

class DialogHelper {

    var currentUser = QBUser()

    fun CreateDialog(userId: Int, request: Request, context: Context){

        currentUser = SharedPreferences.GetCurrentUser()

        val occupantIdsList = ArrayList<Int>()
        occupantIdsList.add(userId)

        val chatService = QBChatService.getInstance()
        val isLoggedIn = chatService.isLoggedIn

        val dialog = DialogUtils.buildPrivateDialog(userId)

        if(isLoggedIn == true) {
            CreateDialog(dialog, request, context)
        }else{
            Autentication.Relogin(currentUser, object : QBEntityCallback<Void> {
                override fun onSuccess(p0: Void?, p1: Bundle?) {
                    CreateDialog(dialog, request, context)
                }

                override fun onError(e: QBResponseException?) {
                    Log.e(TAG, "Relogin $e")
                }
            })
        }
    }

    fun CreateDialog(dialog: QBChatDialog, request: Request, context: Context){
        val dbHelper = DatabaseHelperImpl(DataBaseBuilder.getInstance(context))

        QBRestChatService.createChatDialog(dialog).performAsync(object :
            QBEntityCallback<QBChatDialog> {
            override fun onSuccess(result: QBChatDialog?, params: Bundle?) {
                Log.e(ContentValues.TAG, "result:       $result")
                GlobalScope.launch {
                    dbHelper.removeRequest(request)
                }
                Utils.OpenChatActivity(context, dialog)
                JoinDialog(result!!)
            }
            override fun onError(responseException: QBResponseException?) {
                Log.e(ContentValues.TAG, "${responseException}")
            }
        })
    }

    fun JoinDialog(dialog: QBChatDialog){
        Log.e(ContentValues.TAG, "Joinnnn:    $dialog")
        val history = DiscussionHistory()
        history.maxStanzas = 0
        SendMessage(dialog, "Heyy")
        //ReadMessage(dialog)

            dialog.join(history, object : QBEntityCallback<Void> {
                override fun onSuccess(o: Void?, bundle: Bundle?) {
                    Log.e(TAG, "Join Good")
                    //SendMessage(dialog, "Message Sent")
                }

                override fun onError(e: QBResponseException?) {
                    Log.e(TAG, "Join Error: ${e!!.message}")
                }
            })

    }

    fun SendMessage(dialog: QBChatDialog, Message: String){

        val chatMessage = QBChatMessage()
        chatMessage.body = Message
        chatMessage.setSaveToHistory(true)
        chatMessage.dateSent = System.currentTimeMillis() / 1000
        chatMessage.isMarkable = true

        dialog.sendMessage(chatMessage, object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                Log.i(ContentValues.TAG, "Mensagge Sent $chatMessage")
            }

            override fun onError(e: QBResponseException?) {
                Log.e(ContentValues.TAG, "Message: $e")
            }
        })



    }

    fun GetDialogs(): MutableLiveData<ArrayList<QBChatDialog>> {
        var listDialogs: ArrayList<QBChatDialog> = ArrayList()
        var List = MutableLiveData<ArrayList<QBChatDialog>>()
        val requestBuilder = QBRequestGetBuilder()
        requestBuilder.limit = 50

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(object :
            QBEntityCallback<ArrayList<QBChatDialog>> {
            override fun onSuccess(result: ArrayList<QBChatDialog>?, params: Bundle?) {
                Log.e(TAG, "ListDialog: ${result}")
                listDialogs = result!!
                List.value = listDialogs
            }

            override fun onError(responseException: QBResponseException?) {
                Log.e(TAG, "Error ListDialog: $responseException")
            }
        })
        return List
    }


    fun Join(dialog: QBChatDialog){
        val history = DiscussionHistory()
        history.maxStanzas = 0
        dialog.join(history, object : QBEntityCallback<Void> {
            override fun onSuccess(o: Void?, bundle: Bundle?) {
            }

            override fun onError(e: QBResponseException?) {
            }
        })
    }

}