package com.gotasoft.mosojkausay_mobile.repositories

import com.gotasoft.mosojkausay_mobile.model.entities.request.PatrocinadorRequest
import com.gotasoft.mosojkausay_mobile.model.entities.response.PatrocinadorResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PatrocinadorRepository {
    suspend fun registarPatrp(patrocinadorRequest: PatrocinadorRequest): Flow<PatrocinadorResponse> =
        flow {
            val res = ApiRest.request.registrarPatrocinador(patrocinadorRequest)
            emit(res)
        }.flowOn(Dispatchers.IO)
}