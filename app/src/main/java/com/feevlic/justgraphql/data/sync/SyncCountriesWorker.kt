package com.feevlic.justgraphql.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feevlic.justgraphql.data.SyncManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncCountriesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val syncManager: SyncManager
) : CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val ok = syncManager.syncAll()
            if (ok) Result.success() else Result.retry()
        } catch (t: Throwable) {
            Result.retry()
        }
    }
}