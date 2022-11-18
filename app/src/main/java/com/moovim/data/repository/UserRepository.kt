package com.moovim.data.repository

import com.moovim.data.local.UserSharedPreferences
import com.moovim.data.remote.dto.common.Api
import com.moovim.data.remote.dto.toUser
import com.moovim.domain.model.User
import com.moovim.util.Result
import com.moovim.util.handleApiResponse
import java.lang.Exception
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class UserRepository @Inject constructor(
    private val api: Api,
    private val userSharedPreferences: UserSharedPreferences,
) {

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val token = api.login(username, password).token
            userSharedPreferences.setUserToken(token)
            Result.Success(token)
        } catch (e: Exception){
            Result.Error("Authentication failed",0)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return userSharedPreferences.isUserLoggedIn()
    }

    suspend fun getCurrentUser() : Result<User>{
        return  handleApiResponse({api.getCurrentUser()},
            { it.toUser() })
    }

    fun getUserCurrentRoutineId(): Int{
        return userSharedPreferences.getUserCurrentRoutineId()
    }

    fun setUserCurrentRoutineId(routineId: Int){
        return userSharedPreferences.setUserCurrentRoutineId(routineId)
    }

    suspend fun logout() : Result<Unit>{
        val result : Result<Unit> = handleApiResponse({api.logout()}, { it })
        if (result is Result.Success){
            userSharedPreferences.deleteUserToken()
        }
        return result
    }

}