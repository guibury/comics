package com.guilhermebury.comics.domain.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemModelResponse(
    @SerializedName("data")
    val data: DataModelResponse
): Serializable

data class DataModelResponse(
    @SerializedName("results")
    val results: List<ResultModelResponse>
): Serializable

data class ResultModelResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailModelResponse
): Serializable

data class ThumbnailModelResponse(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
): Serializable