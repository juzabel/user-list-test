package com.juzabel.network.session

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

class SessionInterceptor(private val sessionManager: SessionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        } ?: getToken(requestBuilder)

        val response = chain.proceed(requestBuilder.build())

        return@runBlocking if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED ||
            response.code == HttpURLConnection.HTTP_FORBIDDEN
        ) {
            refreshToken(requestBuilder)
        } else {
            response
        }
    }

    private fun getToken(requestBuilder: Request.Builder): Request.Builder {
        return requestBuilder
    }

    private fun refreshToken(requestBuilder: Request.Builder): Response {
        return requestBuilder.build().headers.firstOrNull()
            ?.let { Response.Builder().addHeader(it.first, it.second).build() }
            ?: Response.Builder().build()
    }
}
