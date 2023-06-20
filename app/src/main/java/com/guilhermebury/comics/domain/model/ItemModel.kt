package com.guilhermebury.comics.domain.model

import java.io.Serializable

data class ItemModel(
    val data: DataModel
): Serializable

data class DataModel(
    val results: List<ResultModel>
): Serializable

data class ResultModel(
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailModel
): Serializable

data class ThumbnailModel(
    val path: String,
    val extension: String,
): Serializable