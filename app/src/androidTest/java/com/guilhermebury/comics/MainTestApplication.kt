package com.guilhermebury.comics

import android.app.Application
import androidx.startup.AppInitializer
import androidx.work.WorkManagerInitializer

class MainTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this).initializeComponent(WorkManagerInitializer::class.java)
    }
}