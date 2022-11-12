package com.moovim.data.repository

import android.content.SharedPreferences
import com.moovim.data.local.UserSharedPreferences
import com.moovim.data.remote.dto.common.Api
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class UserRepository @Inject constructor(
    private val api: Api,
    private val userSharedPreferences: UserSharedPreferences,
) {

    suspend fun login(username: String, password: String): String {
        val token = api.login(username, password).token
        userSharedPreferences.setUserToken(token);
        return token;
    }

}