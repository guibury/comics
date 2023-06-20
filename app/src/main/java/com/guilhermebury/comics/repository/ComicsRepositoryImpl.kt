package com.guilhermebury.comics.repository

import com.guilhermebury.comics.BuildConfig.API_KEY
import com.guilhermebury.comics.BuildConfig.HASH
import com.guilhermebury.comics.BuildConfig.TS
import com.guilhermebury.comics.domain.mapper.toDomain
import com.guilhermebury.comics.domain.model.ItemModel
import com.guilhermebury.comics.remote.ComicsService
import com.guilhermebury.comics.result.RetrofitResult
import com.guilhermebury.comics.result.Result

class ComicsRepositoryImpl(
    private val comicsService: ComicsService,
    private val retrofitResult: RetrofitResult,
): ComicsRepository {

    override suspend fun getComics(): Result<ItemModel> {
        return retrofitResult.getResult {
            comicsService.getComics(
                apikey = API_KEY,
                ts = TS,
                hash = HASH
            )
        }.map { it.toDomain() }
    }
}