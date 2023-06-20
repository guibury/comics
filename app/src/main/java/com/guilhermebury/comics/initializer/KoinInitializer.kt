package com.guilhermebury.comics.initializer

import android.content.Context
import androidx.startup.Initializer
import com.guilhermebury.comics.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidLogger(Level.ERROR)
            androidContext(context)
            modules(AppModule)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = mutableListOf()
}