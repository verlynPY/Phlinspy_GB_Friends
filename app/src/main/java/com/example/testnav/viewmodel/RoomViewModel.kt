package com.example.testnav.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.emit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnav.R
import com.example.testnav.model.DAO.DataBaseBuilder
import com.example.testnav.model.DAO.DatabaseHelper
import com.example.testnav.model.DAO.DatabaseHelperImpl
import com.example.testnav.model.Request
import com.example.testnav.model.SettingFilter
import com.example.testnav.view.ManagerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RoomViewModel: ViewModel() {

    private var MDialog = ManagerDialog()

    private val _uiState = MutableStateFlow(RequestByIdUiState.Success(null))

    val uiState: StateFlow<RequestByIdUiState> = _uiState

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

}