package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.ExerciseImage

data class ExerciseImageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("url")
    val url: String
)

fun ExerciseImageDto.toExerciseImage(): ExerciseImage {
    return ExerciseImage(
        id = id,
        number = number,
        url = url
    )
}

