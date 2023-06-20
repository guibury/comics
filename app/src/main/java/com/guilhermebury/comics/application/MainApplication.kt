package com.guilhermebury.comics.application

import android.app.Application
import androidx.startup.AppInitializer
import com.guilhermebury.comics.initializer.KoinInitializer

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this).initializeComponent(KoinInitializer::class.java)
    }
}