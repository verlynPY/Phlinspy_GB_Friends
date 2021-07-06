package com.example.testnav.model.DAO

import com.example.testnav.model.Request
import com.example.testnav.model.Utils.ID

class DatabaseHelperImpl(private val appDatabase: AppDatabase): DatabaseHelper {

    override suspend fun getRequets(): List<Request> = appDatabase.requestDao().GettAll()

    override suspend fun getRequestsById(): List<Request> = appDatabase
        .requestDao().GetRequestById("oxGvYueyE4hflxgkEJEH9YBuLFf1")

    override suspend fun updateStatusView(ID: String) = appDatabase
        .requestDao().UpdateStatusView(true, ID)

    override suspend fun insertRequest(request: Request) = appDatabase.requestDao().AddRequest(request)

}