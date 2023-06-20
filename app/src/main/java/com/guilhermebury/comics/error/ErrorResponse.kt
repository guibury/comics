package com.guilhermebury.comics.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(
    @SerializedName("code")
    val code: String = "",

    @SerializedName("message")
    val message: String = "",
) {
    fun isValid(): Boolean = code.isNotEmpty() && message.isNotEmpty()
}