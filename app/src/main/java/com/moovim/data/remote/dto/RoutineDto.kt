package com.moovim.data.remote


import com.google.gson.annotations.SerializedName

data class RoutineDto(
    @SerializedName("category")
    val category: CategoryDto,
    @SerializedName("date")
    val date: Long,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isPublic")
    val isPublic: Boolean,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("name")
    val name: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("user")
    val user: UserDto
)