package com.juzabel.util.result

sealed class AResult<T, E> {
    class Success<T, E>(val value: T) : AResult<T, E>()
    class Failure<T, E>(val error: E) : AResult<T, E>()
}
