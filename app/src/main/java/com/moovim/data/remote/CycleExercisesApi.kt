package com.moovim.data.remote

import com.moovim.data.remote.dto.CycleExerciseDto
import com.moovim.data.remote.dto.ExerciseDto
import com.moovim.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CycleExercisesApi {

    @GET("cycles/{cycleId}/exercises")
    suspend fun getCycleExercises(
        @Path("cycleId") cycleId: Int,
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "order",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<CycleExerciseDto>

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun getCycleExercise(
        @Path("cycleId") cycleId: Int,
        @Path("exerciseId") exerciseId: Int
    ): ExerciseDto

}