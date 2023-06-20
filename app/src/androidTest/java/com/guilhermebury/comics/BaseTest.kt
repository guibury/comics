package com.guilhermebury.comics

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.guilhermebury.comics.di.AppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.koin.test.junit5.AutoCloseKoinTest
import org.koin.test.junit5.KoinTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class)
open class BaseTest : KoinTest {

    private val testContext: Context by lazy { ApplicationProvider.getApplicationContext() }
    protected open val testDependenciesModules: List<Module> = listOf()

    @JvmField
    @RegisterExtension
    val koinTest = KoinTestExtension.create {
        androidLogger(Level.ERROR)
        androidContext(testContext)
        modules(AppModule + testDependenciesModules)
    }

    protected fun Module.toList() = listOf(this)
}