package com.moovim.data.remote


import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)