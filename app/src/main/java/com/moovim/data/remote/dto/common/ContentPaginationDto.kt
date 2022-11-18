package com.moovim.data.remote.dto.common

import com.google.gson.annotations.SerializedName

data class ContentPaginationDto<T>(
    @SerializedName("content")
    val content: List<T> = emptyList(),
    @SerializedName("direction")
    val direction: String,
    @SerializedName("isLastPage")
    val isLastPage: Boolean,
    @SerializedName("orderBy")
    val orderBy: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)

data class TokenDto(
    @SerializedName("token")
    val token: String,
)

fun TokenDto.toToken(): String {
    return token
}