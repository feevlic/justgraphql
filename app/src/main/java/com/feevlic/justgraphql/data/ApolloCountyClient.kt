package com.feevlic.justgraphql.data

import com.feevlic.justgraphql.domain.CountryClient
import com.feevlic.justgraphql.domain.DetailedCountry
import com.apollographql.apollo3.ApolloClient

import com.feevlic.justgraphql.domain.SimpleCountry

class ApolloCountyClient (private val apolloClient: ApolloClient): CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        TODO("Implement getCountries using apolloClient")
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        TODO("Implement getCountry using apolloClient")
    }
}