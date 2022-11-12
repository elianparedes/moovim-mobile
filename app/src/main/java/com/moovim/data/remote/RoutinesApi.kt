package com.moovim.data.remote

import com.moovim.data.remote.dto.common.ResponseDto
import com.moovim.data.remote.dto.RoutineDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutinesApi {

    @GET("user/current/routines")
    suspend fun getCurrentUserRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<RoutineDto>

    @GET("routines")
    suspend fun getAllRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<RoutineDto>

}