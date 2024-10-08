package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.response.AgregarContadorRes
import com.gotasoft.mosojkausay_mobile.model.entities.response.ContadorRes
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ContadorRepository {
    suspend fun getContador(): Flow<ContadorRes> = flow {
        val res = ApiRest.request.getContador()
        emit(res)
    }.flowOn(Dispatchers.IO)

    suspend fun addContador(): Flow<AgregarContadorRes> = flow {
        val res = ApiRest.request.agregarContador()
        emit(res)
    }.flowOn(Dispatchers.IO)
}