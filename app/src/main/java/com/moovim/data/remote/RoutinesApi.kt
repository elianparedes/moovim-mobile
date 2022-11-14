package com.moovim.data.remote

import com.moovim.data.remote.dto.RoutineDto
import com.moovim.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutinesApi {

    @GET("users/current/routines")
    suspend fun getCurrentUserRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<RoutineDto>

    @Headers("No-Auth: true")
    @GET("routines")
    suspend fun getAllRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc",
        @Query("search") search: String? = null
    ): ResponseDto<RoutineDto>

    @GET("routines/{routineId}")
    suspend fun getRoutine(@Path("routineId") routineId: Int): RoutineDto
}