package com.moovim.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferences
@Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getUserToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun setUserToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun isUserLoggedIn(): Boolean{
        return sharedPreferences.contains("token")
    }

}