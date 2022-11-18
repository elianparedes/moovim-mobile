package com.moovim.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.io.IOException

sealed class Result<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String? = null, code: Int) : Result<T>(null,message ,code)
}

data class ResponseError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    var details: List<String>? = null
)

suspend fun <T : Any, E: Any> handleApiResponse(
    execute: suspend () -> Response<T>,
    transform: (T) -> E
): Result<E> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val data = transform(body)
            Result.Success(data)
        } else if (response.errorBody() != null) {
            val gson = Gson()
            val error = gson.fromJson(
                response.errorBody()!!.charStream().readText(),
                ResponseError::class.java
            )
            Result.Error(error.description, error.code)
        } else
            Result.Error(response.message(), response.code())
    } catch (e: IOException) {
        Result.Error(e.message.toString(), 98)
    } catch (e: Exception) {
        Result.Error(e.message.toString(), 99)
    }
}

