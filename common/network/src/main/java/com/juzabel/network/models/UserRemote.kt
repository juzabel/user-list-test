package com.juzabel.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRemote(
    val avatar: String,
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    val id: Long,
    @Json(name = "last_name")
    val lastName: String
)
