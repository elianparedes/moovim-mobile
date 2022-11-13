package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.User

data class CycleDto(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("repetitions")
    val repetitions: Int,
    @SerializedName("type")
    val type: String
)
fun CycleDto.toCycle(): Cycle {
    return Cycle(
        id = id,
        name = name,
        detail = detail,
        repetitions = repetitions,
        order = order,
        cycleExercises = emptyList()
    )
}