package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ExerciseMetadataDto(
    @SerializedName("pos")
    val pos: String,
    @SerializedName("procedure")
    val procedure: String
)

