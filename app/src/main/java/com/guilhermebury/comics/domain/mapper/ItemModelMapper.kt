package com.guilhermebury.comics.domain.mapper

import com.guilhermebury.comics.domain.model.DataModel
import com.guilhermebury.comics.domain.model.ItemModel
import com.guilhermebury.comics.domain.model.ResultModel
import com.guilhermebury.comics.domain.model.ThumbnailModel
import com.guilhermebury.comics.domain.response.ItemModelResponse
import com.guilhermebury.comics.domain.response.ThumbnailModelResponse

fun ItemModelResponse.toDomain() =
    ItemModel(
        data = this.buildData()
    )

fun ItemModelResponse.buildData() =
    DataModel (
        results = this.buildResults()
    )

fun ItemModelResponse.buildResults() =
    this.data.results.map {
        ResultModel(
            title = it.title,
            description = it.description,
            thumbnail = it.thumbnail.buildThumbnail()
        )
    }

fun ThumbnailModelResponse.buildThumbnail() =
    ThumbnailModel(
        path = this.path,
        extension = this.extension
    )