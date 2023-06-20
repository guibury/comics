package com.guilhermebury.comics.remote

import com.guilhermebury.comics.domain.response.ItemModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {

    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Response<ItemModelResponse>

}