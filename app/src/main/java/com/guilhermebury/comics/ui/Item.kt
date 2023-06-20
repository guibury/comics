package com.guilhermebury.comics.ui

import androidx.annotation.Keep

@Keep
data class Item(
    val image: String,
    val title: String,
    val description: String
)