package com.moovim.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.Exercise

data class ExerciseDto(
    @SerializedName("date")
    val date: Long,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("metadata")
    val metadata: ExerciseMetadataDto
)

fun ExerciseDto.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        detail = detail,
        pos = metadata.pos,
        procedure = metadata.procedure
    )
}