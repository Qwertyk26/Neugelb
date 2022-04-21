package de.neugelb.domain.error

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import de.neugelb.domain.exception.ServerException
import de.neugelb.presentation.ui.extensions.toValidString
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ErrorType {
    object UnknownError : ErrorType()
    object NoDataFetched : ErrorType()
    class ServerError(val error: String?, val code: Int?) : ErrorType()
    object TimeoutError : ErrorType()
}

fun transformException(t: Throwable): ErrorType = when (t) {
    is UnknownHostException -> ErrorType.TimeoutError
    is SocketTimeoutException -> ErrorType.TimeoutError
    is ServerException -> ErrorType.ServerError(t.errorBody.convertServerErrorToString(), t.code)
    else -> ErrorType.UnknownError
}

fun getExceptionMessage(t: Throwable): String? = when (t) {
    is ServerException -> t.errorBody.convertServerErrorToString()
    else -> ""
}

private fun JsonElement?.convertServerErrorToString() = when {
    this == null -> ""
    this.isJsonObject -> this.asJsonObject.convertServerErrorToString()
    else -> toValidString()
}

private fun JsonObject.convertServerErrorToString() = when {
    this.get("errorMessage")?.isJsonObject == true -> this.get("errorMessage").asJsonObject.get("errorCode").asString
    this.get("error") != null -> this.get("error").toValidString()
    else -> this.toValidString()
}