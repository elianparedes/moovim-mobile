package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName

data class RoutineMetadataDto(
    @SerializedName("image")
    val image: String
)