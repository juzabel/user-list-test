package com.juzabel.util.result

class ThrowableError(private val errorCode: Int? = null, private val throwable: Throwable) :
    Error(errorCode ?: throwable.hashCode(), throwable.stackTraceToString())
