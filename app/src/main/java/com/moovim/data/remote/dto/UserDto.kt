package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("avatarUrl")
    val avatarUrl: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastActivity")
    val lastActivity: Long,
    @SerializedName("username")
    val username: String
)