package com.juzabel.util.result

sealed class AResult<T, E : Error> {
    class Success<T, E : Error>(val value: T) : AResult<T, E>()
    class Failure<T, E : Error>(val error: Error) : AResult<T, E>()
}
