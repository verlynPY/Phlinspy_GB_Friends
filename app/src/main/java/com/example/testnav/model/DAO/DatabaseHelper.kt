package com.example.testnav.model.DAO

import com.example.testnav.model.Request
import com.example.testnav.model.SettingFilter
import java.util.*

interface DatabaseHelper {

    suspend fun getRequets(): List<Request>

    suspend fun getRequestsById(): List<Request>

    suspend fun updateStatusView(ID: String)

    suspend fun insertRequest(request: Request)

}

