package com.moovim.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewRoutineReviewDto(
    @SerializedName("score")
    val score: Int,
    @SerializedName("review")
    val review: String
)
