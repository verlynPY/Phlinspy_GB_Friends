package com.example.testnav.model

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.testnav.view.ChatActivity
import com.example.testnav.view.HomeActivity
import com.example.testnav.view.MainActivity
import com.example.testnav.view.RequestActivity
import com.google.gson.Gson
import com.quickblox.chat.model.QBChatDialog

object Utils {

    val bundle = Bundle()
    val gson = Gson()

    fun OpenRequetAtivity(context: Context, request: Request){
            val intent = Intent(context, RequestActivity::class.java)
            val requestJsonString = gson.toJson(request)
            bundle.putCharSequence("Id", request.FriendId)
            bundle.putCharSequence("UserName", request.UserName)
            bundle.putString("REQUEST", requestJsonString)
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
    }

    fun OpenChatActivity(context: Context, dialog: QBChatDialog){
            val intent = Intent(context, ChatActivity::class.java)
            val personJsonString = gson.toJson(dialog)
            bundle.putString("DIALOG", personJsonString)
            Log.e(ContentValues.TAG, "$personJsonString")
            intent.putExtras(bundle)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

    }

    fun OpenHomeActivity(context: Context){
        val intent = Intent(context, HomeActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun OpenMainActivity(context: Context, activity: Activity){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        activity.finish()
    }

    fun CloseSession(context: Context){
        val intent = Intent(context, HomeActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    const val dataBaseName = "request"

    const val ID = "oxGvYueyE4hflxgkEJEH9YBuLFf1"

}