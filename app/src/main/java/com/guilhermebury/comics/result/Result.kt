package com.guilhermebury.comics.result

import com.guilhermebury.comics.error.ComicsError

class Result<T> private constructor(private val value: T?, private val error: ComicsError?) {

    suspend fun onSuccess(action: suspend (value: T) -> Unit): Result<T> {
        value?.let { action(it) }
        return this
    }

    suspend fun onFailure(action: suspend (ComicsError) -> Unit): Result<T> {
        error?.let { action(it) }
        return this
    }

    suspend fun onAny(action: suspend (Result<T>) -> Unit): Result<T> {
        value?.let { action(this) }
        error?.let { action(this) }
        return this
    }

    suspend fun <V> map(action: suspend (T) -> V): Result<V> {
        if (value == null) return Result(value, error)
        return success(action(value))
    }

    companion object {
        fun <T> success(data: T) = Result(value = data, error = null)
        fun <T> failure(error: ComicsError) = Result<T>(value = null, error = error)
    }
}