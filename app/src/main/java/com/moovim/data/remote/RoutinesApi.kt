package com.moovim.data.remote

import com.moovim.data.remote.dto.NewRoutineReviewDto
import com.moovim.data.remote.dto.RoutineDto
import com.moovim.data.remote.dto.RoutineReviewDto
import com.moovim.data.remote.dto.common.ResponseDto
import com.moovim.data.remote.dto.common.TokenDto
import com.moovim.domain.model.RoutineReview
import retrofit2.http.*

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

    @GET("favourites")
    suspend fun getAllFavouriteRoutines(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
    ): ResponseDto<RoutineDto>

    @POST("favourites/{routineId}")
    suspend fun addRoutineToFavourites(
        @Path("routineId") routineId: Int
    )

    @DELETE("favourites/{routineId}")
    suspend fun deleteRoutineFromFavourites(
        @Path("routineId") routineId: Int
    )

    @GET("reviews/{routineId}")
    suspend fun getAllRoutineReviews(
        @Path("routineId") routineId: Int, @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc",
    ): ResponseDto<RoutineReviewDto>

    @POST("reviews/{routineId}")
    suspend fun addRoutineReview(
        @Path("routineId") routineId: Int,
        @Body routineReview: NewRoutineReviewDto
    ) /* TODO: POST RoutineReview response */
}