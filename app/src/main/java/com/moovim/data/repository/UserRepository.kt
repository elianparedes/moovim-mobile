package com.moovim.data.repository

import com.moovim.data.local.UserSharedPreferences
import com.moovim.data.remote.dto.common.Api
import com.moovim.util.Response
import java.lang.Exception
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class UserRepository @Inject constructor(
    private val api: Api,
    private val userSharedPreferences: UserSharedPreferences,
) {

    suspend fun login(username: String, password: String): Response<String> {
        return try {
            val token = api.login(username, password).token
            userSharedPreferences.setUserToken(token)
            Response.Success(token)
        } catch (e: Exception){
            Response.Error("Authentication failed")
        }
    }

    fun isUserLoggedIn(): Boolean {
        return userSharedPreferences.isUserLoggedIn()
    }

    fun getUserCurrentRoutineId(): Int{
        return userSharedPreferences.getUserCurrentRoutineId()
    }

    fun setUserCurrentRoutineId(routineId: Int){
        return userSharedPreferences.setUserCurrentRoutineId(routineId)
    }

}