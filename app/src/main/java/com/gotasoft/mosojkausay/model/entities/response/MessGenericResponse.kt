package com.gotasoft.mosojkausay.model.entities.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessGenericResponse(var success: Boolean = false,
                               var message: String = "")