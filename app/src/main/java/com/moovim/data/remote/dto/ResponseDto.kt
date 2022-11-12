package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
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