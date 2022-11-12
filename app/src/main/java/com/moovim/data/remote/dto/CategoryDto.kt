package com.moovim.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.moovim.domain.model.Category

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("detail")
    val detail: String
)

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        detail = detail
    )
}
