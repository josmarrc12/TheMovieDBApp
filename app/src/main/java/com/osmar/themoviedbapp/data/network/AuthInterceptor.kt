package com.osmar.themoviedbapp.data.network

import com.osmar.themoviedbapp.data.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader(ApiConstants.HEADER_AUTH, ApiConstants.API_KEY)
        }.build()
        return chain.proceed(request)
    }

}