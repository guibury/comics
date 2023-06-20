package com.guilhermebury.comics

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class InstantExecutorExtension : CoroutinesTestExtension() {
    override fun beforeAll(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun postToMainThread(runnable: Runnable) = runnable.run()
            override fun isMainThread(): Boolean = true
        })

        super.beforeAll(context)
    }

    override fun afterAll(context: ExtensionContext?) {
        super.afterAll(context)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}