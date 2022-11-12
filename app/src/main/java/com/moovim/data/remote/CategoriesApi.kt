package com.moovim.data.remote

import com.moovim.data.remote.dto.CategoryDto
import com.moovim.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoriesApi {

    @GET("categories")
    suspend fun getAllCategories(
        @Query("page") page: Number = 0,
        @Query("size") size: Number = 10,
        @Query("orderBy") orderBy: String = "date",
        @Query("direction") direction: String = "asc"
    ): ResponseDto<CategoryDto>

    @GET("categories/{categoryId}")
    suspend fun getCategory(@Path("categoryId") categoryId: Int): CategoryDto

}