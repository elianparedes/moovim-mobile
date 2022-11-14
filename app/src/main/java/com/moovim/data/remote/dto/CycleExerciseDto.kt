package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.CycleExercise
import com.moovim.domain.model.Exercise

data class CycleExerciseDto(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("exercise")
    val exercise: ExerciseDto,
    @SerializedName("order")
    val order: Int,
    @SerializedName("repetitions")
    val repetitions: Int
)

fun CycleExerciseDto.toCycleExercise(): CycleExercise {
    return CycleExercise(
        duration = duration,
        exercise = exercise.toExercise(),
        order = order,
        repetitions = repetitions
    )
}