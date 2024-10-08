package com.gotasoft.mosojkausay_mobile.repositories

import android.util.Log
import com.gotasoft.mosojkausay_mobile.model.entities.response.MensajeResponse
import com.gotasoft.mosojkausay_mobile.model.entities.response.MessageResponse
import com.gotasoft.mosojkausay_mobile.model.network.ApiRest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MensajeRepository {

    suspend fun getMensajes(token: String = "",
                            value: String = "",
                            items: Int = 10,
                            page: Int = 1): Flow<ArrayList<MensajeResponse>> =
        flow {
            val res = ApiRest.request.getMensajes(token, value, items, page)
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)

    suspend fun getMensajesAll(token: String = "",
                            value: String = "",
                            items: Int = 10,
                            page: Int = 1): Flow<ArrayList<MessageResponse>> =
        flow {
            val res = ApiRest.request.getMensajesAll(token, value, items, page)
            Log.e("RES", res.toString())
            emit(ArrayList(res))
        }.flowOn(Dispatchers.IO)
}