package com.guilhermebury.comics.result

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.guilhermebury.comics.error.CONNECTION_ERROR
import com.guilhermebury.comics.error.ErrorResponse
import com.guilhermebury.comics.error.GENERIC_ERROR
import com.guilhermebury.comics.error.toError
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class RetrofitResultImpl(private val gson: Gson) : RetrofitResult {

    override suspend fun <T> getResult(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request.invoke()
            val data = response.body()

            if (response.isSuccessful && data != null) {
                Result.success(data)
            } else {
                handleError(response.errorBody())
            }
        } catch (expected: IOException) {
            handleException(expected)
        } catch (expected: RuntimeException) {
            handleException(expected)
        }
    }

    private fun <T> handleError(errorBody: ResponseBody?): Result<T> {
        if (errorBody == null) return Result.failure(GENERIC_ERROR)

        val json = errorBody.string()
        val error = try {
            val errorObject = gson.fromJson(json, ErrorResponse::class.java)

            if (errorObject == null || !errorObject.isValid()) {
                GENERIC_ERROR
            } else {
                errorObject.toError()
            }
        } catch (exception: JsonSyntaxException) {
            GENERIC_ERROR
        }
        return Result.failure(error)
    }

    private fun <T> handleException(exception: Exception): Result<T> {
        return when (exception) {
            is UnknownHostException -> Result.failure(CONNECTION_ERROR)
            else -> Result.failure(GENERIC_ERROR)
        }
    }
}