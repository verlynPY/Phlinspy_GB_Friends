package com.example.testnav.model.QuickBlox

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.request.QBMessageGetBuilder
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import org.jivesoftware.smackx.muc.DiscussionHistory

class ChatHelper {

    var listMessage: ArrayList<QBChatMessage> = ArrayList()

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


    fun ReadMessage(qbChatDialog: QBChatDialog): ArrayList<QBChatMessage>{
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 50

        QBRestChatService.getDialogMessages(qbChatDialog, messageGetBuilder)
            .performAsync(object : QBEntityCallback<ArrayList<QBChatMessage>> {
            override fun onSuccess(qbChatMessages: ArrayList<QBChatMessage>?, bundle: Bundle?) {
                Log.e(ContentValues.TAG, "Mensagessss  ${qbChatMessages}")
                listMessage = qbChatMessages!!
            }
            override fun onError(e: QBResponseException?) {
                Log.e(TAG, "$e")
            }
        })

        return listMessage
    }

    suspend fun ReadChatHistory(dialog: QBChatDialog, callback: QBEntityCallback<ArrayList<QBChatMessage>>){
        val messageGetBuilder = QBMessageGetBuilder()
        messageGetBuilder.limit = 50

        QBRestChatService.getDialogMessages(dialog, messageGetBuilder).performAsync(object : QbEntityCallbackWrapper<ArrayList<QBChatMessage>>(callback),
            QBEntityCallback<java.util.ArrayList<QBChatMessage>> {
            override fun onSuccess(p0: java.util.ArrayList<QBChatMessage>?, p1: Bundle?) {
                callback.onSuccess(p0,p1)
            }
        })
    }

}