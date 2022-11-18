package com.moovim.data.remote.dto.common


import com.google.gson.annotations.SerializedName

data class ResponseErrorDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<String>
)

class ResponseErrorException(
    code: Int,
    message: String,
    details: List<String>?
) : Exception(message)