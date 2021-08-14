package com.example.testnav.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.emit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.R
import com.example.testnav.model.DAO.DataBaseBuilder
import com.example.testnav.model.DAO.DatabaseHelper
import com.example.testnav.model.DAO.DatabaseHelperImpl
import com.example.testnav.model.QuickBlox.ChatHelper
import com.example.testnav.model.QuickBlox.DialogHelper
import com.example.testnav.model.Request
import com.example.testnav.model.SettingFilter
import com.example.testnav.view.ManagerDialog
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class RoomViewModel: ViewModel() {

    private var MDialog = ManagerDialog()

    private val _uiState = MutableStateFlow(RequestByIdUiState.Success(null))
    val uiState: StateFlow<RequestByIdUiState> = _uiState

    private val dialogHelper: DialogHelper = DialogHelper()

    private val chatHelper: ChatHelper = ChatHelper()

    private val _chatUiState = MutableStateFlow(ChatUiState.Success(null))
    val chatUiState: StateFlow<ChatUiState> = _chatUiState


    fun AddRequest(request: Request, context: Context, view: View){
        viewModelScope.launch(Dispatchers.Main) {
            try{
                val dbHelper = DatabaseHelperImpl(DataBaseBuilder.getInstance(context))
                val db = dbHelper.insertRequest(request)
                MDialog.ToastSuccessfully(context, view, "Request Successfully")
            }catch (e: Exception){
                Log.e(TAG, "Error: $e")
                MDialog.ToastSuccessfully(context, view, "Request: ${e.message}")
            }
        }
    }

    fun getRequests(context: Context){
        viewModelScope.launch {
            try {
                val dbHelper = DatabaseHelperImpl(DataBaseBuilder.getInstance(context))
                val db = dbHelper.getRequets()
                Log.i(TAG, "Requesttt: $db")
            } catch (e: Exception) {
                Log.e(TAG, "Error: $e")
            }
        }
    }

    fun UpdateStatusView(context: Context,IdFriend: String){
        try{
            viewModelScope.launch(Dispatchers.Main) {
                val dbHelperImpl = DatabaseHelperImpl(DataBaseBuilder.getInstance(context))
                val db = dbHelperImpl.updateStatusView(IdFriend)
            }
        } catch(e: Exception){
                Log.e(TAG, "$e")
            }
        }



    fun EmitRequestsById(context: Context){
        viewModelScope.launch(Dispatchers.Main){
            getRequestsById(context)
                .flowOn(Dispatchers.IO)
                .collect { item ->
                    _uiState.value = RequestByIdUiState.Success(item)
                }
        }
    }

   fun ReadDialogs() : MutableLiveData<ArrayList<QBChatDialog>>{
       return dialogHelper.GetDialogs()
    }

    fun CreateDialog(UserId: Int){
        try {
            dialogHelper.CreateDialog(UserId)
        }
        catch (e: Exception){
            Log.e(TAG, "$e")
        }
    }

    fun SendMessage(dialog: QBChatDialog, Message: Any){
        try {
            chatHelper.SendMessage(dialog, Message.toString())
        }catch (e: Exception){
            Log.e(TAG, "Sending Message Exception: $e")
        }
    }

    fun EmitChat(qbChatDialog: QBChatDialog){
        viewModelScope.launch(Dispatchers.Main){
            readMessage(qbChatDialog)
                .flowOn(Dispatchers.IO)
                .collect{ item ->
                    _chatUiState.value = ChatUiState.Success(item)
                }
        }
    }

    fun ReadMessage(dialog: QBChatDialog): Flow<ArrayList<QBChatMessage>> = flow{
        try {
                /*val chatMessage = chatHelper.ReadMessage(dialog)
                emit(chatMessage)
                Log.e(TAG, "Reading Message: $chatMessage")*/
                    var list: ArrayList<QBChatMessage> = ArrayList()
            chatHelper.ReadChatHistory(dialog, object : QBEntityCallback<ArrayList<QBChatMessage>>{
                override fun onSuccess(p0: ArrayList<QBChatMessage>?, p1: Bundle?) {
                    Log.e(TAG, "Reading Message: $p0")
                    list = p0!!
                }
                override fun onError(p0: QBResponseException?) {
                    Log.e(TAG, "Reading Message Exception: $p0")
                }
            })
            Log.e(TAG, "Reading Messageeeeeeeeee: $list")
            emit(list)
        }catch (e: Exception){
            Log.e(TAG, "Reading Message Exceptionn: $e")
        }
    }


    suspend fun readMessage(dialog: QBChatDialog): Flow<ArrayList<QBChatMessage>> {
        val historyDeferred = CompletableDeferred<ArrayList<QBChatMessage>>()
        chatHelper.ReadChatHistory(dialog, object : QBEntityCallback<ArrayList<QBChatMessage>> {
            override fun onSuccess(listmessage: ArrayList<QBChatMessage>?, p1: Bundle?) {
                historyDeferred.complete(listmessage ?: arrayListOf())
            }

            override fun onError(p0: QBResponseException?) {
                historyDeferred.completeExceptionally(p0 ?: CancellationException())
            }
        })
        return flow {
            try {
                emit(historyDeferred.await())
                Log.e(TAG, "Reading Messageeee ${historyDeferred.await()}")
            } catch (e: Exception) {
                Log.e(TAG, "Reading Message Exceptionn: $e")
            }
        }
    }

    private fun getRequestsById(context: Context) = flow{
            try {
                val dbHelper = DatabaseHelperImpl(DataBaseBuilder.getInstance(context))
                val db = dbHelper.getRequestsById()
                emit(db)
                Log.e(TAG, "Requesttt IDDDDDD: $db")
            } catch (e: Exception) {
                Log.e(TAG, "Error: $e")
            }
    }

    sealed class RequestByIdUiState{
        data class Success(val requests: List<Request>?): RequestByIdUiState()
        data class Error(val exception: Throwable): RequestByIdUiState()
    }

    sealed class ChatUiState{
        data class Success(val dialog: ArrayList<QBChatMessage>?): ChatUiState()
        data class Error(val exception: Throwable): ChatUiState()
    }

}