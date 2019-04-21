package com.ed.mygithub.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor : Interceptor {
    companion object {
        var user: String = ""
        var password: String = ""
        var auth: String = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return if (user.isNotEmpty() && password.isNotEmpty()) {
            val credentials = Credentials.basic(user, password)
            auth = credentials
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(request)
        }
    }
}