package com.moovim.di

import com.moovim.data.local.UserSharedPreferences
import com.moovim.data.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Inspired in [Making Retrofit Work For You]
 * (https://jakewharton.com/making-retrofit-work-for-you/)
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val userSharedPreferences: UserSharedPreferences
) : Interceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        if (request.header("No-Auth") == null){
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer ${userSharedPreferences.getUserToken()}")
                .build();
        }

        return chain.proceed(request);
    }
}