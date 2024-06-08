package com.juzabel.network.services

import com.juzabel.network.models.ResponseRemote
import com.juzabel.network.models.UserRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUserList(@Query("page") page: Int): ResponseRemote<List<UserRemote>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Long): ResponseRemote<UserRemote>
}
