package com.juzabel.errors

sealed class Error(val code: Int, val message: String) {
    class Unknown(message: String) : Error(UNKNOWN, message)
}

private const val UNKNOWN = 1000
