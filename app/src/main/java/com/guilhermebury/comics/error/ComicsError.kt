package com.guilhermebury.comics.error

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsError(
    val code: String = "",
    val message: String = "",
) : Parcelable