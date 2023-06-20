package com.guilhermebury.comics.error

const val CODE_ERROR_DEFAULT = "DefaultError"
const val MESSAGE_ERROR_DEFAULT = "Unable to load data. Try again"
const val CODE_ERROR_CONNECTION = "ConnectionError"
const val MESSAGE_ERROR_CONNECTION = "No internet connection"

val GENERIC_ERROR = ComicsError(
    code = CODE_ERROR_DEFAULT,
    message = MESSAGE_ERROR_DEFAULT
)

val CONNECTION_ERROR = ComicsError(
    code = CODE_ERROR_CONNECTION,
    message = MESSAGE_ERROR_CONNECTION
)