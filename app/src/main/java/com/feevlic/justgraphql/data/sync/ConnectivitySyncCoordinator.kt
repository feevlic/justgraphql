package com.feevlic.justgraphql.data.sync

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.feevlic.justgraphql.domain.ObserveConnectivityUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectivitySyncCoordinator @Inject constructor(
    private val observeConnectivityUseCase: ObserveConnectivityUseCase,
    @ApplicationContext private val context: Context
) {
    private val workerManager by lazy { WorkManager.getInstance(context) }

    fun start(scope: CoroutineScope) {
        scope.launch(Dispatchers.Default) {
            observeConnectivityUseCase().collectLatest { connected ->
                if (connected) {
                    val request = OneTimeWorkRequestBuilder<SyncCountriesWorker>().build()
                    workerManager.enqueue(request)
                }
            }
        }
    }
}