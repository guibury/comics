package com.guilhermebury.comics.repository

import com.guilhermebury.comics.domain.model.ItemModel
import com.guilhermebury.comics.result.Result

interface ComicsRepository {

    suspend fun getComics(): Result<ItemModel>
}