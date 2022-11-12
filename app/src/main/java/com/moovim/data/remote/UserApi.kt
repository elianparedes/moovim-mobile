package com.moovim.data.remote

import com.moovim.data.remote.dto.UserDto
import com.moovim.data.remote.dto.common.TokenDto
import retrofit2.http.*

interface UserApi {

    @GET("users/current")
    suspend fun getCurrentUser(): UserDto

    @POST("users")
    suspend fun addUser(@Body user: UserDto): UserDto

    @POST("users/resend_verification")
    suspend fun resendVerification(@Field("email") email: String)

    @POST("users/verify_email")
    suspend fun verifyEmail(@Field("email") email: String, @Field("code") code: String)

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): TokenDto

}