package com.example.testnav.model.DAO

import com.example.testnav.model.Request
import com.example.testnav.model.SettingFilter
import com.example.testnav.model.Utils.ID

class DatabaseHelperImpl(private val appDatabase: AppDatabase): DatabaseHelper {

    //Requests
    override suspend fun getRequets(): List<Request> = appDatabase.requestDao().GettAll()

    override suspend fun getRequestsById(ID: String): List<Request> = appDatabase
        .requestDao().GetRequestById(ID)

    override suspend fun updateStatusView(ID: String) = appDatabase
        .requestDao().UpdateStatusView(true, ID)

    override suspend fun insertRequest(request: Request) = appDatabase.requestDao().AddRequest(request)

    override suspend fun removeRequest(request: Request) = appDatabase.requestDao().Delete(request)


}