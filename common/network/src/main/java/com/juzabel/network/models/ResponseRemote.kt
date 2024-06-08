package com.juzabel.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseRemote<T>(
    val data: T,
    val page: Int,
    @Json(name = "per_page")
    val perPage: Int,
    val total: Int,
    @Json(name = "total_pages")
    val totalPages: Int
)
