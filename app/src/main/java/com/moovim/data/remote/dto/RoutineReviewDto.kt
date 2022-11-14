package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.RoutineReview

data class RoutineReviewDto(
    @SerializedName("date")
    val date: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("review")
    val review: String,
    @SerializedName("routine")
    val routine: RoutineDto,
    @SerializedName("score")
    val score: Int
)

fun RoutineReviewDto.toRoutineReview(): RoutineReview {
    return RoutineReview(
        id = id,
        routineId = routine.id,
        score = score
    )
}