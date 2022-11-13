package com.moovim.data.remote

import com.moovim.data.remote.dto.ExerciseDto
import com.moovim.data.remote.dto.ExerciseImageDto
import com.moovim.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExercisesApi {

    @GET("exercises")
    suspend fun getAllExercises(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc",
        @Query("search") search: String? = null
    ): ResponseDto<ExerciseDto>

    @GET("exercises/{exerciseId}")
    suspend fun getExercise(@Path("exerciseId") exerciseId: Int): ExerciseDto

    @GET("exercises/{exerciseId}/images")
    suspend fun getExerciseImages(@Path("exerciseId") exerciseId: Int): ResponseDto<ExerciseImageDto>

}