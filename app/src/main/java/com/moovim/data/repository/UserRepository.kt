package com.moovim.data.repository

import com.moovim.data.remote.dto.common.Api
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class UserRepository @Inject constructor(
    private val api: Api
) {

    suspend fun login(username: String, password: String): String {
        return api.login(username, password).token
    }

}