package com.example.testnav.model.QuickBlox

import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage

interface ChatClient {

    suspend fun SendMessage(dialog: QBChatDialog, Message: Any)

    suspend fun ReadMessage(dialog: QBChatDialog): ArrayList<QBChatMessage>

}