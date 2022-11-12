package com.moovim.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.Routine

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
    val metadata: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("user")
    val user: UserDto
)

fun RoutineDto.toRoutine(): Routine {
    return Routine(
        id = id,
        name = name,
        detail = detail
    )
}