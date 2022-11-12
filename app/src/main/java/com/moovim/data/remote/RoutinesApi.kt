package com.moovim.data.remote

import com.moovim.data.remote.dto.ResponseDto
import com.moovim.data.remote.dto.RoutineDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RoutinesApi {

    companion object {
        const val API_KEY = ""
        const val BASE_URL = "https://moovim-api-dev.up.railway.app/api/"
    }

    @GET("routines")
    suspend fun getAllRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<RoutineDto>

}