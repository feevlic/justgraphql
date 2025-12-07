package com.feevlic.justgraphql.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectivityUseCase @Inject constructor(private val observer: ConnectivityObserver) {
    operator fun invoke(): Flow<Boolean> = observer.isConnected
}