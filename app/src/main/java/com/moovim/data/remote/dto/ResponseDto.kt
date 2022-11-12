package com.moovim.data.remote


import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    @SerializedName("content")
    val content: List<T>,
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