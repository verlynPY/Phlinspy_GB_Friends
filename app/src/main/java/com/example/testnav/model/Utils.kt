package com.example.testnav.model

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.testnav.view.RequestActivity

object Utils {

    fun OpenRequetAtivity(context: Context, user: Request){
        val intent = Intent(context, RequestActivity::class.java)
        val bundle = Bundle()
        bundle.putCharSequence("Id", user.FriendId)
        bundle.putCharSequence("UserName", user.UserName)
        intent.putExtras(bundle)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    const val dataBaseName = "request"

    const val ID = "oxGvYueyE4hflxgkEJEH9YBuLFf1"

}