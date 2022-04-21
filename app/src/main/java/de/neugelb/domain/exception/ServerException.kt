package de.neugelb.domain.exception

import com.google.gson.JsonElement
import java.io.IOException

class ServerException(val code: Int = 0, message: String = "", val errorBody: JsonElement?) : IOException(message)
class UnknownException(message: String? = "") : IOException(message)

data class SendServerExceptionBody(
    val errcode: Int?,
    val message: String?,
    val errmsg: Errmsg?
)

data class Errmsg(
    val errCode: String,
    val invalidField: String
)