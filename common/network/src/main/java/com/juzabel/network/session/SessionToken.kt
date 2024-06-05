package com.juzabel.network.session

data class SessionToken(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String
)
