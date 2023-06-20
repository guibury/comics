package com.guilhermebury.comics.error

fun ErrorResponse.toError() = ComicsError(
    code = this.code,
    message = this.message,
)