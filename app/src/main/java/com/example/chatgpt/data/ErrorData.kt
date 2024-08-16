package com.example.chatgpt.data

sealed class Result<out T, out E> {
    data class Success<out T>(val value: T) : Result<T, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()

    fun isSuccess() = this is Success<T>
    fun isError() = this is Error<E>
}