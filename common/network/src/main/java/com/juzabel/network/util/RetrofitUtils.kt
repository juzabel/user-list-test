package com.juzabel.network.util

import com.juzabel.network.session.SessionInterceptor
import com.juzabel.network.session.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitUtils {

    fun <T> createService(
        sessionManager: SessionManager,
        url: String,
        enableLogging: Boolean = false,
        service: Class<T>
    ): T =
        createService(make(sessionManager, url, enableLogging), service)

    fun <T> createService(retrofit: Retrofit, service: Class<T>): T =
        retrofit.create(service)

    fun make(
        sessionManager: SessionManager,
        url: String,
        enableLogging: Boolean = false
    ): Retrofit {
        val client =
            OkHttpClient.Builder().addInterceptor(SessionInterceptor(sessionManager))
                .build()
        return Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(MoshiConverterFactory.create())
            if (enableLogging) {
                setLoggingInterceptor(this)
            }
        }.build()
    }

    private fun setLoggingInterceptor(builder: Retrofit.Builder) {
        val okHttpClient = OkHttpClient.Builder().apply {
            val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }.build()
        builder.client(okHttpClient)
    }
}
