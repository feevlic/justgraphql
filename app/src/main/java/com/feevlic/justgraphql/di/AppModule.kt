package com.feevlic.justgraphql.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.feevlic.justgraphql.data.ApolloCountyClient
import com.feevlic.justgraphql.data.RoomCountryClient
import com.feevlic.justgraphql.data.SyncManager
import com.feevlic.justgraphql.data.db.CountryDatabase
import com.feevlic.justgraphql.domain.ConnectivityObserver
import com.feevlic.justgraphql.domain.CountryClient
import com.feevlic.justgraphql.domain.GetCountriesUseCase
import com.feevlic.justgraphql.domain.GetCountryUseCase
import com.feevlic.justgraphql.domain.ObserveConnectivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloClient): CountryClient {
        return ApolloCountyClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CountryDatabase {
        return Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            "countries.db"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideCountryClient(apolloClient: ApolloCountyClient, db: CountryDatabase): CountryClient {
        return RoomCountryClient(remote = apolloClient, dao = db.countryDao())
    }

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(countryClient: CountryClient): GetCountriesUseCase {
        return GetCountriesUseCase(countryClient)
    }

    @Provides
    @Singleton
    fun provideSyncManager(
        apolloCountyClient: ApolloCountyClient,
        db: CountryDatabase
    ): SyncManager {
        return SyncManager(apolloCountyClient, db.countryDao())
    }

    @Provides
    @Singleton
    fun provideGetCountryUseCase(countryClient: CountryClient): GetCountryUseCase {
        return GetCountryUseCase(countryClient)
    }

    @Provides
    @Singleton
    fun provideConnectivityUseCase(observer: ConnectivityObserver): ObserveConnectivityUseCase {
        return ObserveConnectivityUseCase(observer)
    }
}