package com.guilhermebury.comics.di

import com.guilhermebury.comics.BuildConfig.BASE_URL
import com.guilhermebury.comics.remote.ComicsService
import com.guilhermebury.comics.repository.ComicsRepository
import com.guilhermebury.comics.repository.ComicsRepositoryImpl
import com.guilhermebury.comics.result.RetrofitResult
import com.guilhermebury.comics.result.RetrofitResultImpl
import com.guilhermebury.comics.viewmodel.ComicsViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val AppModule = module {
    factory<ComicsRepository> { ComicsRepositoryImpl(get(), get()) }
    viewModel { ComicsViewModel(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicsService::class.java)
    }

    single<RetrofitResult> { RetrofitResultImpl(Gson()) }
}