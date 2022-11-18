package com.moovim.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferences
@Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val userCurrentRoutineKey = "current_routine_id"
    private val userTokenKey = "token"

    fun getUserToken(): String? {
        return sharedPreferences.getString(userTokenKey, null)
    }

    fun setUserToken(token: String) {
        sharedPreferences.edit().putString(userTokenKey, token).apply()
    }

    fun isUserLoggedIn(): Boolean{
        return sharedPreferences.contains(userTokenKey)
    }

    fun getUserCurrentRoutineId(): Int{
        return sharedPreferences.getInt(userCurrentRoutineKey, -1)
    }

    fun setUserCurrentRoutineId(routineId: Int) {
        sharedPreferences.edit().putInt(userCurrentRoutineKey, routineId).apply()
    }

    fun deleteUserToken(){
        sharedPreferences.edit().remove(userTokenKey).apply()
    }
}