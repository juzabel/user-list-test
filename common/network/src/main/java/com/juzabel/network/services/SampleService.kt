package com.juzabel.network.services

import com.juzabel.network.models.RemoteTest
import retrofit2.http.GET

interface SampleService {
    @GET("tests")
    suspend fun getTests(): List<RemoteTest>
}
