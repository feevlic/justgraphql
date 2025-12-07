package com.feevlic.justgraphql.di

import com.feevlic.justgraphql.data.AndroidConnectivityObserver
import com.feevlic.justgraphql.domain.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(
        androidConnectivityObserver: AndroidConnectivityObserver
    ): ConnectivityObserver

}


