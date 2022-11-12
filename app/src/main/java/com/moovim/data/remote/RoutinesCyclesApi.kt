package com.moovim.data.remote

import com.moovim.data.remote.dto.CycleDto
import com.moovim.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoutinesCyclesApi {

    @GET("routines/{routineId}/cycles")
    suspend fun getRoutineCycles(
        @Path("routineId") routineId: Int,
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<CycleDto>

    @GET("routines/{routineId}/cycles/{cycleId}")
    suspend fun getRoutineCycle(
        @Path("routineId") routineId: Int,
        @Path("cycleId") cycleId: Int
    ): CycleDto

}